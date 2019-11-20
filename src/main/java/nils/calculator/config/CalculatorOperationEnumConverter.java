package nils.calculator.config;


import nils.calculator.enums.CalculatorOperation;
import org.springframework.core.convert.converter.Converter;

public class CalculatorOperationEnumConverter implements Converter<String, CalculatorOperation> {

    @Override
    public CalculatorOperation convert(String source) {
        try {
            return CalculatorOperation.getEnum(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("Allowed values for CalculatorOperation are: [div, prod, sum, sub] ");
        }
    }
}

