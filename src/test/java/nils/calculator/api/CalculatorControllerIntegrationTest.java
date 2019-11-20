package nils.calculator.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import nils.calculator.CalculatorPostgresqlContainer;
import nils.calculator.MemCacheContainer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllerIntegrationTest {

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

    @Test
    public void getCalculationResult_ReturnsResponse_IfCorrectQuery() {
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
    public void postCalculationResult_CallsServiceWithCorrectParameters() throws JSONException {
        // given
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

        String response = get(uri + "/calculate/results")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

        // then
        assertThat(response).isEqualTo("[{\"id\":1,\"num1\":1.0,\"num2\":3.0,\"operation\":\"sum\",\"result\":4.0}]");
    }
}