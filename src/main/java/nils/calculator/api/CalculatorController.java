package nils.calculator.api;

import lombok.RequiredArgsConstructor;
import nils.calculator.enums.CalculatorOperation;
import nils.calculator.models.CalculationDto;
import nils.calculator.models.Calculation;
import nils.calculator.service.CalculatorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping("calculate")
    public Double getCalculationResult(@RequestParam Double num1,
                                       @RequestParam Double num2,
                                       @RequestParam CalculatorOperation operation) {
        CalculationDto operationData = CalculationDto
                .builder()
                .num1(num1)
                .num2(num2)
                .operation(operation)
                .build();
        return calculatorService.getCalculationResult(operationData, false);
    }


    @CacheEvict(value = "results", allEntries = true)
    @PostMapping("calculate")
    public Double postCalculationResult(@RequestBody CalculationDto calculationDto) {
        return calculatorService.getCalculationResult(calculationDto, true);
    }


    @GetMapping("getResults")
    public List<Calculation> getAllResults() {
        return calculatorService.getAllResults();
    }
}
