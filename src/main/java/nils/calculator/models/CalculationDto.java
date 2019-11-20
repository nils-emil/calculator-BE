package nils.calculator.models;

import lombok.*;
import nils.calculator.enums.CalculatorOperation;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CalculationDto {

    private Double num1;
    private Double num2;
    private CalculatorOperation operation;
}
