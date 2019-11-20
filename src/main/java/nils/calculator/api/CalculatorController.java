package nils.calculator.api;

import lombok.RequiredArgsConstructor;
import nils.calculator.enums.CalculatorOperation;
import nils.calculator.models.OperationQuery;
import nils.calculator.persistance.Calculation;
import nils.calculator.service.CalculatorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping()
    public Double getCalculationResult(@RequestParam Double num1,
                                       @RequestParam Double num2,
                                       @RequestParam CalculatorOperation operation) throws Exception {
        OperationQuery operationData = OperationQuery
                .builder()
                .num1(num1)
                .num2(num2)
                .operation(operation)
                .build();
        return calculatorService.getResult(operationData);
    }


    @CacheEvict(value = "results", allEntries = true)
    @PostMapping()
    public Double postCalculationResult(@RequestBody OperationQuery operationQuery) throws Exception {
        return calculatorService.getResult(operationQuery);
    }


    @GetMapping("results")
    public List<Calculation> getAllResults() {
        return calculatorService.getAllResults();
    }
}
