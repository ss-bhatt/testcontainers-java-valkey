package io.github.ss_bhatt.testcontainers.valkey;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class ValkeyContainerTest {

    @Container
    private static final ValkeyContainer valkey = new ValkeyContainer();

    @Test
    void testStartup() {
        assertTrue(valkey.isRunning());
    }

    @Test
    void testConnectionString() {
        String connectionString = valkey.getConnectionString();
        assertTrue(connectionString.startsWith("valkey://"));
        assertTrue(connectionString.contains(valkey.getHost()));
        assertTrue(connectionString.contains(String.valueOf(valkey.getMappedPort(6379))));
    }

    @Test
    void testSimpleSetGet() {
        try (Jedis jedis = new Jedis(valkey.getHost(), valkey.getMappedPort(6379))) {
            jedis.set("key", "value");
            assertEquals("value", jedis.get("key"));
        }
    }
}
