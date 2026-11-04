import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.emptyString;


public class ProjectWithRESTAssuredTest {

    RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri("https://localhost:8080")
            .setBasePath("/goods/list")
            .addHeader("Content-Type", "application/json")
            .build();

    RequestSpecification reqSpecAdd = new RequestSpecBuilder()
            .setBaseUri("https://localhost:8080")
            .setBasePath("/goods/add")
            .addHeader("Content-Type", "application/json")
            .build();

    @Test
    void getDoGetOne() {
        given()
                .baseUri("https://localhost:8080")
                .header("Content-Type", "application/json")
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body(null);
    }

    @Test
    void getDoGetTwo() {
        given()
                .spec(reqSpec)
                .then()
                .log().all()
                .statusCode(200)
                .body(emptyString());
    }

    @Test
    void getDoGetThree() {
        Response response = given()
                .spec(reqSpecAdd)
                .post("/get");

        response.then().body(emptyString());
    }

    @Test
    void getDoGetFour() {
        Response response = given()
                .spec(reqSpecAdd)
                .post("/get");

        Assertions.assertThat(response.statusCode())
                .as("Must be code 200")
                .isEqualTo(200);
        Assertions.assertThat(response.body())
                .as("Тело должно быть пустое")
                .asString()
                .isEmpty();
    }
}
