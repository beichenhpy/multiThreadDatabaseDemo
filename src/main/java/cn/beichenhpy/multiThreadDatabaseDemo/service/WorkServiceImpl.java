package cn.beichenhpy.multiThreadDatabaseDemo.service;

import cn.beichenhpy.multiThreadDatabaseDemo.mapper.WorkMapper;
import cn.beichenhpy.multiThreadDatabaseDemo.modal.Address;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author hanpengyu
 * @version 1.0
 * @apiNote
 * @since 2021/12/15 16:44
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Address> implements WorkService{

}
