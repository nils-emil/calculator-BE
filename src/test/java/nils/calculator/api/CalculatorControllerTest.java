package nils.calculator.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import nils.calculator.CalculatorPostgresqlContainer;
import nils.calculator.MemCacheContainer;
import nils.calculator.enums.CalculatorOperation;
import nils.calculator.models.CalculationDto;
import nils.calculator.service.CalculatorService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.get;
import static nils.calculator.enums.CalculatorOperation.ADD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllerTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = CalculatorPostgresqlContainer.getInstance();

    @ClassRule
    public static GenericContainer memCacheContainer = MemCacheContainer.getInstance();

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @MockBean
    CalculatorService calculatorService;

    @Test
    public void getCalculationResult_ReturnsResponse_IfCorrectQuery() {
        // given
        CalculationDto query = CalculationDto
                .builder()
                .num1(2.1)
                .num2(4.05)
                .operation(ADD)
                .build();
        when(calculatorService.getCalculationResult(query, false)).thenReturn(6.15);

        // when
        String response = get(uri + "/calculate?num1=2.1&num2=4.05&operation=sum")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

        // then
        assertThat(response).isEqualTo("6.15");
    }

    @Test
    public void getCalculationResult_ReturnsResponse_IfDivison() {
        // given
        CalculationDto query = CalculationDto
                .builder()
                .num1(7D)
                .num2(2D)
                .operation(CalculatorOperation.DIVIDE)
                .build();
        when(calculatorService.getCalculationResult(query, false)).thenReturn(3.5);

        // when
        String response = get(uri + "/calculate?num1=7&num2=2&operation=div")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

        // then
        assertThat(response).isEqualTo("3.5");
    }

    @Test
    public void getCalculationResult_ReturnsResponse_IfMultiply() {
        // given
        CalculationDto query = CalculationDto
                .builder()
                .num1(7D)
                .num2(2D)
                .operation(CalculatorOperation.MULITIPLY)
                .build();
        when(calculatorService.getCalculationResult(query, false)).thenReturn(14D);

        // when
        String response = get(uri + "/calculate?num1=7&num2=2&operation=prod")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

        // then
        assertThat(response).isEqualTo("14.0");
    }

    @Test
    public void getCalculationResult_ReturnsResponse_IfSubtract() {
        // given
        CalculationDto query = CalculationDto
                .builder()
                .num1(7D)
                .num2(2D)
                .operation(CalculatorOperation.SUBTRACT)
                .build();
        when(calculatorService.getCalculationResult(query, false)).thenReturn(5D);

        // when
        String response = get(uri + "/calculate?num1=7&num2=2&operation=sub")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

        // then
        assertThat(response).isEqualTo("5.0");
    }

    @Test
    public void getCalculationResult_ReturnsBadRequest_IfInvalidOperator() {
        // given
        CalculationDto query = CalculationDto
                .builder()
                .num1(7D)
                .num2(2D)
                .operation(CalculatorOperation.SUBTRACT)
                .build();
        when(calculatorService.getCalculationResult(query, false)).thenReturn(14D);

        // then
        get(uri + "/calculate?num1=7&num2=2&operation=randomOperator")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getCalculationResult_ReturnsBadRequest_IfNum1Missing() {
        // then
        get(uri + "/calculate?num2=2&operation=sub")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getCalculationResult_ReturnsBadRequest_IfNum2Missing() {
        // then
        get(uri + "/calculate?num1=2&operation=sub")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getCalculationResult_ReturnsBadRequest_IfOperatorMissing() {
        // then
        get(uri + "/calculate?num1=1&num2=2")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postCalculationResult_CallsServiceWithCorrectParameters() throws JSONException {
        RequestSpecification request = RestAssured.given();
        JSONObject jsonObj = new JSONObject()
                .put("num1", "1")
                .put("num2", "3")
                .put("operation", "sum");
        request.body(jsonObj.toString());
        request.contentType("application/json");

        // then
        request.post(uri + "/calculate")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        verify(calculatorService, times(1)).getCalculationResult(CalculationDto
                .builder()
                .num1(1D)
                .num2(3D)
                .operation(ADD)
                .build(), true);
    }

    @Test
    public void getAllResults_CallsServiceWithCorrectParameters() {
        // then
        get(uri + "/calculate/results")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        verify(calculatorService, times(1)).getAllResults();
    }

}