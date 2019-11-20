package nils.calculator.api;

import nils.calculator.CalculatorPostgresqlContainer;
import nils.calculator.MemCacheContainer;
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
public class HelloWorldControllerTest {

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
    public void getHelloWorld_ReturnsHello() {
        // then
        String response = get(uri + "/hello")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

        assertThat(response).isEqualTo("Hello World!");
    }

}