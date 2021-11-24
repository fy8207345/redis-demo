package com.fy.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import org.junit.Test;

public class LettuceTests {
    @Test
    public void connect() {
        RedisURI redisURI = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withDatabase(6)
                .build();
        RedisClient client = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisReactiveCommands<String, String> reactive = connection.reactive();
        reactive.set("key1", "value1")
                .subscribe(s -> {
                    System.out.println("set success" + s);
                });
        connection.close();
    }
}
