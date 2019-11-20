package nils.calculator.models;

import lombok.*;
import nils.calculator.enums.CalculatorOperation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calculatons", schema = "calculator")
public class Calculation implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private Double num1;

    @Column
    @NotNull
    private Double num2;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private CalculatorOperation operation;

    @Column
    @NotNull
    private Double result;

}
