package com.fy;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class Tests {
    @Test
    public void connect() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.connect();
        jedis.select(6);
        jedis.set("key", "value");
        System.out.println(jedis.get("key"));
        jedis.close();
    }
}
