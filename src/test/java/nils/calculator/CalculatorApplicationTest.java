package nils.calculator;

import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculatorApplicationTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = CalculatorPostgresqlContainer.getInstance();

    @ClassRule
    public static GenericContainer memCacheContainer = MemCacheContainer.getInstance();

    @Test
    public void main() {
        // Test class added ONLY to cover main() invocation not covered by application tests.
        CalculatorApplication.main(new String[]{});
        CalculatorApplication main = new CalculatorApplication();
        assertThat(main).isInstanceOf(CalculatorApplication.class);
    }

}