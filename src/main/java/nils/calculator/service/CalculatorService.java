package nils.calculator.service;

import lombok.RequiredArgsConstructor;
import nils.calculator.enums.CalculatorOperation;
import nils.calculator.models.Calculation;
import nils.calculator.models.CalculationDto;
import nils.calculator.persistance.CalculationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatorService {

    private final CalculationRepository calculationRepository;

    public Double getCalculationResult(CalculationDto query, boolean saveResult) {
        validateQuery(query);
        Calculation operationResult;
        switch (query.getOperation()) {
            case SUBTRACT:
                operationResult = constructAndPersistResult(query, query.getNum1() - query.getNum2());
                break;
            case DIVIDE:
                operationResult = constructAndPersistResult(query, query.getNum1() / query.getNum2());
                break;
            case MULITIPLY:
                operationResult = constructAndPersistResult(query, query.getNum1() * query.getNum2());
                break;
            case ADD:
                operationResult = constructAndPersistResult(query, query.getNum1() + query.getNum2());
                break;
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
        if (saveResult) {
            calculationRepository.save(operationResult);
        }
        return operationResult.getResult();
    }

    private void validateQuery(CalculationDto query) {
        if (query.getOperation() == CalculatorOperation.DIVIDE && query.getNum2().equals(0D)) {
            throw new IllegalArgumentException("Division by zero not allowed");
        }
    }

    @Cacheable("results")
    public List<Calculation> getAllResults() {
        return calculationRepository.findAll();
    }

    private Calculation constructAndPersistResult(CalculationDto operation, Double result) {
        return Calculation.builder()
                .num1(operation.getNum1())
                .num2(operation.getNum2())
                .operation(operation.getOperation())
                .result(result)
                .build();
    }
}
