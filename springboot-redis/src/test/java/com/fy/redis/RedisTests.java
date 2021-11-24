package com.fy.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void name() {
        redisTemplate.opsForValue().set("key1", "value1");
        String key1 = redisTemplate.opsForValue().get("key1");
        System.out.println(key1);

    }
}
