package io.github.ss_bhatt.testcontainers.valkey;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcontainers implementation for Valkey.
 */
public class ValkeyContainer extends GenericContainer<ValkeyContainer> {

    private static final String DEFAULT_IMAGE_NAME = "docker.io/valkey/valkey";
    private static final String DEFAULT_TAG = "8.0";
    private static final int VALKEY_PORT = 6379;

    /**
     * Create a new Valkey container with the default image and tag.
     */
    public ValkeyContainer() {
        this(DockerImageName.parse(DEFAULT_IMAGE_NAME + ":" + DEFAULT_TAG));
    }

    /**
     * Create a new Valkey container with the specified image name.
     * @param dockerImageName the image name to use
     */
    public ValkeyContainer(final String dockerImageName) {
        this(DockerImageName.parse(dockerImageName));
    }

    /**
     * Create a new Valkey container with the specified image name.
     * @param dockerImageName the image name to use
     */
    public ValkeyContainer(final DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DockerImageName.parse(DEFAULT_IMAGE_NAME));
        withExposedPorts(VALKEY_PORT);
        setWaitStrategy(org.testcontainers.containers.wait.strategy.Wait.forListeningPort());
    }

    /**
     * Get the connection string for the Valkey container.
     * @return the connection string in the format "valkey://host:port"
     */
    public String getConnectionString() {
        return "valkey://" + getHost() + ":" + getMappedPort(VALKEY_PORT);
    }
}
