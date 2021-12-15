package cn.beichenhpy.multiThreadDatabaseDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author hanpengyu
 * @version 1.0
 * @apiNote
 * @since 2021/12/15 16:48
 */
@Slf4j
@Service
public class Entry {


    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    private AtomicBoolean all_ok = new AtomicBoolean(true);
    private List<Boolean> all_state = new CopyOnWriteArrayList<>();

    public void multi() {
        CountDownLatch main = new CountDownLatch(1);
        CountDownLatch children = new CountDownLatch(2);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            final int innerI = i;
            executorService.execute(() -> {
                TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
                TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
                try {
                    log.info("{}已开始", Thread.currentThread().getName());
//                    if (innerI == 1){
//                        throw new RuntimeException();
//                    }
                    insert();
                    log.info("{},1", Thread.currentThread().getName());
                    all_state.add(true);
                    log.info("{},2", Thread.currentThread().getName());
                    children.countDown();
                    log.info("{}已完成，等待是否提交", Thread.currentThread().getName());
                    main.await();
                    if (all_ok.get()) {
                        platformTransactionManager.commit(transaction);
                    } else {
                        platformTransactionManager.rollback(transaction);
                    }
                } catch (Exception e) {
                    log.info("{},3", Thread.currentThread().getName());
                    all_state.add(false);
                    log.info("{},4", Thread.currentThread().getName());
                    children.countDown();
                    log.error("error:{}", e.getMessage());
                }
            });
        }
        //主线程
        try {
            //等待子线程全部完成
            children.await();
            for (Boolean state : all_state) {
                if (!state) {
                    all_ok.set(false);
                    break;
                }
            }
            main.countDown();
        } catch (Exception e) {
            log.error("错误：{}", e.getMessage());
        }
    }

    private void insert() {
        StringBuffer sql = new StringBuffer();
        sql.append("    INSERT INTO ADDRESS VALUES(?,?,?)   \n");
        Object[] args = {1, "1", "1"};
        int update = jdbcTemplate.update(sql.toString(), args);
        if (update == 0){
            throw new RuntimeException();
        }
    }

}
