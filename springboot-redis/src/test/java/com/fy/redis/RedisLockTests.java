package com.fy.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
@SpringBootTest
public class RedisLockTests {

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Test
    void lockTests() throws IOException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Lock lock = redisLockRegistry.obtain("myValue");
                boolean acquired = false;
                try {
                    acquired = lock.tryLock(5, TimeUnit.SECONDS);
                    if(acquired){
                        log.info("acquired: {}", acquired);
                        TimeUnit.SECONDS.sleep(3);
                        log.info("executed");
                    }else{
                        log.info("not acquired lock!");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(acquired){
                        lock.unlock();
                    }
                }
            }
        };

        Thread thread1 = new Thread(runnable, "1");
        Thread thread2 = new Thread(runnable, "2");
        Thread thread3 = new Thread(runnable, "3");
        thread1.start();
        thread2.start();
        thread3.start();

        TimeUnit.SECONDS.sleep(10);
    }
}
