package nils.calculator.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class CalculatorOperationTest {

    @Test
    void getEnum_ReturnEnum_IdProd() {
        // when
        CalculatorOperation prod = CalculatorOperation.getEnum("prod");

        // then
        assertThat(prod).isEqualTo(CalculatorOperation.MULITIPLY);
    }

    @Test
    void getEnum_ReturnEnum_IfDiv() {
        // when
        CalculatorOperation prod = CalculatorOperation.getEnum("div");

        // then
        assertThat(prod).isEqualTo(CalculatorOperation.DIVIDE);
    }

    @Test
    void getEnum_ReturnEnum_IfAdd() {
        // when
        CalculatorOperation prod = CalculatorOperation.getEnum("sum");

        // then
        assertThat(prod).isEqualTo(CalculatorOperation.ADD);
    }

    @Test
    void getEnum_ReturnEnum_IfSub() {
        // when
        CalculatorOperation prod = CalculatorOperation.getEnum("sub");

        // then
        assertThat(prod).isEqualTo(CalculatorOperation.SUBTRACT);
    }

    @Test
    void getEnum_ThrowException_IfInvalidInput() {
        // when
        Throwable result = catchThrowable(() -> CalculatorOperation.getEnum("random-string"));

        // then
        assertThat(result).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid enum value");
    }

    @Test
    void toValue_ReturnValue_IdSum() {
        // when
        String prod = CalculatorOperation.ADD.toValue();

        // then
        assertThat(prod).isEqualTo("sum");
    }

    @Test
    void toValue_ReturnValue_IfDivision() {
        // when
        String prod = CalculatorOperation.DIVIDE.toValue();

        // then
        assertThat(prod).isEqualTo("div");
    }

    @Test
    void toValue_ReturnValue_IfSubtract() {
        // when
        String prod = CalculatorOperation.SUBTRACT.toValue();

        // then
        assertThat(prod).isEqualTo("sub");
    }

    @Test
    void toValue_ReturnValue_IfMultiply() {
        // when
        String prod = CalculatorOperation.MULITIPLY.toValue();

        // then
        assertThat(prod).isEqualTo("prod");
    }

    @Test
    void toString_ReturnsCorrectResult() {
        // when
        String prod = CalculatorOperation.MULITIPLY.toString();

        // then
        assertThat(prod).isEqualTo("prod");
    }
}