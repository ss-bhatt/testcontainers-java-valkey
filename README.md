# Testcontainers Valkey Module for Java

[![Maven Central](https://img.shields.io/maven-central/v/io.github.ss-bhatt/testcontainers-valkey.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.ss-bhatt/testcontainers-valkey)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

The official community implementation of the Valkey Testcontainer for Java, designed for feature parity with the Go and Node.js ecosystems.

## Introduction

Testcontainers Valkey is a Java module that provides a lightweight, throwaway instance of [Valkey](https://valkey.io/) for integration testing. It allows you to reliably test your application against a real Valkey instance, ensuring that your code works as expected in production.

This module closes the gap for Enterprise Java teams migrating from Redis to Valkey, offering a drop-in replacement for existing Redis containers.

## Maven Coordinates

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.ss-bhatt</groupId>
    <artifactId>testcontainers-valkey</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

## Usage

Using `ValkeyContainer` is straightforward and similar to other Testcontainers modules.

```java
import io.github.ss_bhatt.testcontainers.valkey.ValkeyContainer;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.Jedis;

@Testcontainers
public class ValkeyTest {

    @Container
    private static final ValkeyContainer valkey = new ValkeyContainer();

    @Test
    void testValkey() {
        // Obtain connection details
        String host = valkey.getHost();
        Integer port = valkey.getMappedPort(6379);

        // Connect using a client like Jedis
        try (Jedis jedis = new Jedis(host, port)) {
            jedis.set("key", "value");
            assert "value".equals(jedis.get("key"));
        }
    }
}
```

## Migration from Redis

If you are currently using `GenericContainer` with a Redis image or trying to adapt a Redis container, switching to `ValkeyContainer` is simple.

**Before:**

```java
GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.0"))
    .withExposedPorts(6379);
```

**After:**

```java
ValkeyContainer valkey = new ValkeyContainer();
// or with a specific version
// ValkeyContainer valkey = new ValkeyContainer("valkey/valkey:8.0");
```

## Enterprise Features

### Connection String

You can easily retrieve the connection string for the container:

```java
String connectionString = valkey.getConnectionString();
// Returns "valkey://host:port"
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.