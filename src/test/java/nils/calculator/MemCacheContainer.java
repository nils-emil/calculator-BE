package nils.calculator;

import org.testcontainers.containers.GenericContainer;

public class MemCacheContainer extends GenericContainer<MemCacheContainer> {
    private static final String IMAGE_VERSION = "memcached:alpine";
    private static MemCacheContainer container;

    private MemCacheContainer() {
        super(IMAGE_VERSION);
    }

    public static MemCacheContainer getInstance() {
        if (container == null) {
            container = new MemCacheContainer();
        }
        return container;
    }
 
    @Override
    public void start() {
        super.start();
        System.setProperty("MEMCACHE_PORT", container.getMappedPort(11211).toString());
    }
 
    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}