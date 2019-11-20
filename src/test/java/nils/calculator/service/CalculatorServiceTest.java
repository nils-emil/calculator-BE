package nils.calculator.service;

import nils.calculator.enums.CalculatorOperation;
import nils.calculator.models.Calculation;
import nils.calculator.models.CalculationDto;
import nils.calculator.persistance.CalculationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorServiceTest {

    @Mock
    CalculationRepository calculationRepository;

    @InjectMocks
    CalculatorService calculatorService;

    @Test
    public void getCalculationResult_ReturnsCorrectResult_IfCorrectQuery() throws Exception {
        // given
        CalculationDto calculationDto = CalculationDto
                .builder()
                .num1(1D)
                .num2(2D)
                .operation(CalculatorOperation.ADD)
                .build();

        // when
        Double calculationResult = calculatorService.getCalculationResult(calculationDto, false);

        // then
        verify(calculationRepository, times(0)).save(any());
        assertThat(calculationResult).isEqualTo(3D);
    }

    @Test
    public void getCalculationResult_ThrowsException_IfDivisionByZero() {
        // given
        CalculationDto calculationDto = CalculationDto
                .builder()
                .num1(1D)
                .num2(0D)
                .operation(CalculatorOperation.DIVIDE)
                .build();

        // when
        Throwable calculationResult = catchThrowable(() -> {
            calculatorService.getCalculationResult(calculationDto, false);
        });

        // then
        assertThat(calculationResult).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Division by zero not allowed");
    }

    @Test
    public void getCalculationResult_ThrowsException_IfOperatorIsDivision()  {
        // given
        CalculationDto calculationDto = CalculationDto
                .builder()
                .num1(1D)
                .num2(2D)
                .operation(CalculatorOperation.DIVIDE)
                .build();

        // when
        Double calculationResult = calculatorService.getCalculationResult(calculationDto, false);

        // then
        verify(calculationRepository, times(0)).save(any());
        assertThat(calculationResult).isEqualTo(0.5D);
    }

    @Test
    public void getCalculationResult_ReturnsResponse_IfOperatorIsMultiply() {
        // given
        CalculationDto calculationDto = CalculationDto
                .builder()
                .num1(8D)
                .num2(2D)
                .operation(CalculatorOperation.MULITIPLY)
                .build();

        // when
        Double calculationResult = calculatorService.getCalculationResult(calculationDto, false);

        // then
        verify(calculationRepository, times(0)).save(any());
        assertThat(calculationResult).isEqualTo(16D);
    }

    @Test
    public void getCalculationResult_ReturnsResponse_IfOperatorIsSubtract() {
        // given
        CalculationDto calculationDto = CalculationDto
                .builder()
                .num1(8D)
                .num2(2D)
                .operation(CalculatorOperation.SUBTRACT)
                .build();

        // when
        Double calculationResult = calculatorService.getCalculationResult(calculationDto, false);

        // then
        verify(calculationRepository, times(0)).save(any());
        assertThat(calculationResult).isEqualTo(6D);
    }

    @Test
    public void getCalculationResult_ThrowsException_IfOperatorIsPlus() throws Exception {
        // given
        CalculationDto calculationDto = CalculationDto
                .builder()
                .num1(7D)
                .num2(2D)
                .operation(CalculatorOperation.ADD)
                .build();

        // when
        Double calculationResult = calculatorService.getCalculationResult(calculationDto, false);

        // then
        verify(calculationRepository, times(0)).save(any());
        assertThat(calculationResult).isEqualTo(9D);
    }

    @Test
    public void getCalculationResult_ThrowsException_IfOperatorMinus() throws Exception {
        // given
        CalculationDto calculationDto = CalculationDto
                .builder()
                .num1(7D)
                .num2(2D)
                .operation(CalculatorOperation.ADD)
                .build();

        // when
        Double calculationResult = calculatorService.getCalculationResult(calculationDto, false);

        // then
        verify(calculationRepository, times(0)).save(any());
        assertThat(calculationResult).isEqualTo(9D);
    }

    @Test
    public void getCalculationResult_PersistsResult_IfSaveResultFlagTrue() throws Exception {
        // given
        CalculationDto calculationDto = CalculationDto
                .builder()
                .num1(7D)
                .num2(2D)
                .operation(CalculatorOperation.ADD)
                .build();

        // when
        Double calculationResult = calculatorService.getCalculationResult(calculationDto, true);

        // then
        verify(calculationRepository, times(1)).save(any());
        assertThat(calculationResult).isEqualTo(9D);
    }

    @Test
    public void getCalculationResult_ReturnsResult_IfQueriedFromDatabase() {
        // given
        List<Calculation> expectedResult = singletonList(Calculation
                .builder()
                .id(1L)
                .num1(2D)
                .num2(3D)
                .operation(CalculatorOperation.SUBTRACT)
                .result(4d)
                .build());
        when(calculationRepository.findAll()).thenReturn(expectedResult);

        // when
        List<Calculation> calculationResult = calculatorService.getAllResults();

        // then
        assertThat(calculationResult).isEqualTo(expectedResult);
    }
}