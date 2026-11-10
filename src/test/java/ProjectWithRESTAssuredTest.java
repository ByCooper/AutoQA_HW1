import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class ProjectWithRESTAssuredTest {

    RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .addHeader("Content-Type", "application/json")
            .build();

    RequestSpecification reqSpecAdd = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setAuth(basic("admin", "secret123"))
            .addHeader("Content-Type", "application/json")
            .build();

    public record RequestAdd(String name, Double price) {}

    @Test
    void getDoGetOne() {
        given()
                .spec(reqSpec)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(anyOf(is(200), is(201)))
                .body(notNullValue())
                .body("goods", empty());
    }
    //не работает
    @Test
    void getDoGetTwo() {
        given()
                .spec(reqSpec)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    void getDoGetThree() {
        Response response = given()
                .spec(reqSpecAdd)
                .body("""
                        {
                        "name" : "Помидорка",
                        "price": 50.0
                        }
                        """)
                .when()
                .post("/goods/add");

        response.then()
                .statusCode(200)
                .body(notNullValue())
                .body("goods.name", hasItem("Помидорка"));
    }


    @Test
    void getDoGetFour() {
        Response response = given()
                .spec(reqSpecAdd)
                .auth()
                .basic("admin", "secret123")
                .get("/goods/list");

        Assertions.assertThat(response.statusCode())
                .as("Must be code 200")
                .isEqualTo(200)
                .isBetween(200, 201);
        Assertions.assertThat(response.body())
                .as("Тело должно быть пустое")
                .asString()
                .isEmpty();
    }
}
