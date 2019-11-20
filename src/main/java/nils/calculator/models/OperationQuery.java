package nils.calculator.models;

import lombok.*;
import nils.calculator.enums.CalculatorOperation;

@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationQuery {

    private Double num1;
    private Double num2;
    private CalculatorOperation operation;
}
