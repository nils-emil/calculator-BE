package nils.calculator;

import org.testcontainers.containers.PostgreSQLContainer;

public class CalculatorPostgresqlContainer extends PostgreSQLContainer<CalculatorPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:11.1";

    private static CalculatorPostgresqlContainer container;


    private CalculatorPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static CalculatorPostgresqlContainer getInstance() {
        if (container == null) {
            container = new CalculatorPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}