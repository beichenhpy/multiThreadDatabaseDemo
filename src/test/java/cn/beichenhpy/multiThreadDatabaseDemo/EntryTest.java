package cn.beichenhpy.multiThreadDatabaseDemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EntryTest {

    @Resource
    private Entry entry;

    @Test
    void multi() {
        for (int i = 0; i < 1; i++) {
            entry.multi();
        }
    }



}