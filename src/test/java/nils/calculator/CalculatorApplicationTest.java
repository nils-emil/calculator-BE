package nils.calculator;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculatorApplicationTest {

    @Test
    public void main() {
        // Test class added ONLY to cover main() invocation not covered by application tests.
        CalculatorApplication.main(new String[]{});
        CalculatorApplication main = new CalculatorApplication();
        assertThat(main).isInstanceOf(CalculatorApplication.class);
    }

}