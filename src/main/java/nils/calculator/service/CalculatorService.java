package nils.calculator.service;

import lombok.RequiredArgsConstructor;
import nils.calculator.models.OperationQuery;
import nils.calculator.persistance.Calculation;
import nils.calculator.persistance.CalculationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatorService {

    private final CalculationRepository calculationRepository;

    public Double getResult(OperationQuery operation) throws Exception {
        Calculation operationResult;
        switch (operation.getOperation()) {
            case SUBTRACT:
                operationResult = constructResult(operation, operation.getNum1() - operation.getNum2());
                calculationRepository.save(operationResult);
                return operationResult.getResult();
            case DIVIDE:
                operationResult = constructResult(operation, operation.getNum1() / operation.getNum2());
                calculationRepository.save(operationResult);
                return operationResult.getResult();
            case MULITIPLY:
                operationResult = constructResult(operation, operation.getNum1() * operation.getNum2());
                calculationRepository.save(operationResult);
                return operationResult.getResult();
            case ADD:
                operationResult = constructResult(operation, operation.getNum1() + operation.getNum2());
                calculationRepository.save(operationResult);
                return operationResult.getResult();
        }
        throw new Exception();
    }

    @Cacheable("results")
    public List<Calculation> getAllResults() {
        return calculationRepository.findAll();
    }

    private Calculation constructResult(OperationQuery operation, Double result) {
        return Calculation.builder()
                .num1(operation.getNum1())
                .num2(operation.getNum2())
                .operation(operation.getOperation().toString())
                .result(result)
                .build();
    }
}
