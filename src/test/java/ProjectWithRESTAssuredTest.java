import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class ProjectWithRESTAssuredTest {

    RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .addHeader("Content-Type", "application/json")
            .build();

    RequestSpecification reqSpecAuth = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setAuth(basic("admin", "secret123"))
            .addHeader("Content-Type", "application/json")
            .build();

    public record RequestAdd(String name, Double price) {}

    /**
     * Тестирование метода GET /goods/list - Список всех товаров
     * Используются инструкции given(), when(), then()
     */
    @Tag("api")
    @Test
    void testReadOne() {
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


    /**
     * Тестирование метода GET /goods/list - Список всех товаров
     * Используются инструкции given(), when(), then() + RequestSpecification
     */
    @Tag("api")
    @Test
    void testReadTwo() {
        given()
                .spec(reqSpec)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods", empty());
    }


    /**
     * Тестирование метода POST /goods/add - Добавление новых товаров
     * Используются инструкции given(), when(), then() + RequestSpecification
     * Для проверки результата используются встроенные проверки REST Assured
     */
    @Tag("api")
    @Test
    void testAddOne() {
        Response response = (Response) given()
                .spec(reqSpecAuth)
                .body("""
                       {
                        "name" : "Tomato",
                        "price" : 200.0
                        }
                       """)
                .when()
                .post("/goods/add");

        given()
                .spec(reqSpec)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem("Tomato"));
    }


    /**
     * Тестирование метода POST /goods/add - Добавление новых товаров
     * Используются инструкции given(), when(), then() + RequestSpecification
     * Для проверки результата используются встроенные проверки REST Assured + AssertJ
     */
    @Tag("api")
    @Test
    void testAddTwo() {
        Response response = given()
                .spec(reqSpecAuth)
                .body("""
                        {
                        "name" : "Brot",
                        "price": 500.0
                        }
                        """)
                .when()
                .post("/goods/add");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 200")
                .isEqualTo(200);

        Response responseGet = given()
                .spec(reqSpec)
                .when()
                .get("/goods/list");

        Assertions.assertThat(responseGet.statusCode())
                .as("Ожидается получение код операции 200")
                .isEqualTo(200);
        Assertions.assertThat(responseGet.jsonPath().getString("goods[4].name"))
                .as("Тестируемая проверка")
                .contains("Burger");
    }

    /**
     * Тестирование метода POST /goods/add - Добавление новых товаров/Ошибка 400
     * Используются инструкции given(), when() + RequestSpecification
     * Для проверки результата используются встроенные проверки REST Assured + AssertJ
     */
    @Tag("api")
    @Test
    void testAddError() {
        Response response = given()
                .spec(reqSpecAuth)
                .body("""
                        {
                        "name" : "Burger",
                        "price": 500.0
                        }
                        """)
                .when()
                .post("/goods/add");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 400")
                .isEqualTo(400);
    }


    /**
     * Тестирование метода GET /goods/{id} - Получение товара по id
     * Используются инструкции given(), when(), then() + RequestSpecification
     */
    @Tag("api")
    @Test
    void testGetId() {
        Response response = given()
                .spec(reqSpecAuth)
                .pathParam("id", 14)
                .when()
                .get("/goods/{id}");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 200")
                .isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("name"))
                .as("Ожидается получение значения: Хлеб")
                .contains("Хлеб");
    }

    /**
     * Тестирование метода GET /goods/{id} - Получение товара по id/ Ошибка 404
     * Используются инструкции given(), when(), then() + RequestSpecification
     */
    @Tag("api")
    @Test
    void testGetIdError() {
        Response response = given()
                .spec(reqSpecAuth)
                .pathParam("id", 0)
                .when()
                .get("/goods/{id}");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 404")
                .isEqualTo(404);
    }


    /**
     * Тестирование метода DELETE /goods/{id} - Удаление товара
     * Используются инструкции given(), when(), then() + RequestSpecification
     */
    @Tag("api")
    @Test
    void testDelete() {
        Response response = given()
                .spec(reqSpecAuth)
                .pathParam("id", 26)
                .when()
                .delete("/goods/{id}");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 200")
                .isEqualTo(200);

        Response responseGet = given()
                .spec(reqSpecAuth)
                .pathParam("id", 26)
                .when()
                .get("/goods/{id}");

        Assertions.assertThat(responseGet.statusCode())
                .as("Ожидается получение код операции 404")
                .isEqualTo(404);
    }



    /**
     * Тестирование метода DELETE /goods/{id} - Удаление товара/ Ошибка 404
     * Используются инструкции given(), when(), then() + RequestSpecification
     */
    @Tag("api")
    @Test
    void testDeleteError() {
        Response response = given()
                .spec(reqSpecAuth)
                .pathParam("id", 24)
                .when()
                .delete("/goods/{id}");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 404")
                .isEqualTo(404);
    }


    /**
     * Тестирование метода PATCH /goods/{id} - Частичное обновление товара
     * Используются инструкции given(), when(), then() + RequestSpecification
     * Для проверки результата используются встроенные проверки REST Assured + AssertJ
     */
    @Tag("api")
    @Test
    void testUpdate() {
        Response response = given()
                .spec(reqSpecAuth)
                .body("""
                        {
                        "name" : "Хлеб",
                        "price": 500.0
                        }
                        """)
                .pathParam("id", 27)
                .when()
                .patch("/goods/{id}");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 200")
                .isEqualTo(200);

        Response responseGet = given()
                .spec(reqSpecAuth)
                .pathParam("id", 27)
                .when()
                .get("/goods/{id}");

        Assertions.assertThat(responseGet.statusCode())
                .as("Ожидается получение код операции 200")
                .isEqualTo(200);
        Assertions.assertThat(responseGet.jsonPath().getString("name"))
                .as("Ожидается получение значения: Хлеб")
                .contains("Хлеб");
        Assertions.assertThat(responseGet.jsonPath().getDouble("price"))
                .as("Ожидается получение значения: 500")
                .isEqualTo(500.0);
    }


    /**
     * Тестирование метода PATCH /goods/{id} - Частичное обновление товара/ Ошибка 404
     * Используются инструкции given(), when(), then() + RequestSpecification
     * Для проверки результата используются встроенные проверки REST Assured + AssertJ
     */
    @Tag("api")
    @Test
    void testUpdateError() {
        Response response = given()
                .spec(reqSpecAuth)
                .body("""
                        {
                        "name" : "Хлеб",
                        "price": 500.0
                        }
                        """)
                .pathParam("id", 28)
                .when()
                .patch("/goods/{id}");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 404")
                .isEqualTo(404);
    }


    /**
     * Тестирование метода PATCH /goods/{id} - Частичное обновление товара/ Ошибка 400
     * Используются инструкции given(), when(), then() + RequestSpecification
     * Для проверки результата используются встроенные проверки REST Assured + AssertJ
     */
    @Tag("api")
    @Test
    void testUpdateError400() {
        Response response = given()
                .spec(reqSpecAuth)
                .body("""
                        {
                        "name" : "Хлеб",
                        "price": 
                        }
                        """)
                .pathParam("id", 27)
                .when()
                .patch("/goods/{id}");

        Assertions.assertThat(response.statusCode())
                .as("Ожидается получение код операции 400")
                .isEqualTo(400);
    }
}
