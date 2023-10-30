package br.edu.ifsul.estoque;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class ProdutoResourceTest {

    @Test
    public void testGetProdutosEndpoint() {
        given()
                .when().get("/produto")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetProdutoByIdEndpoint() {
        Long id = 1L;

        given()
                .pathParam("id", id)
                .when().get("/produto/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @Transactional
    public void testCreateProdutoEndpoint() {
        String request = "{\n" +
                "\t\"nome\": \"violão\",\n" +
                "\t\"descricao\": \"violão bagual\",\n" +
                "\t\"valor\": 600\n" +
                "}";

        given()
                .contentType("application/json")
                .body(request)
                .when().post("/produto")
                .then()
                .statusCode(201);
    }

    @Test
    @Transactional
    public void testUpdateProdutoEndpoint() {
        String request = "{\n" +
                "\t\"nome\": \"violão\",\n" +
                "\t\"descricao\": \"alterado\",\n" +
                "\t\"valor\": 600\n" +
                "}";
        Long id = 3L;

        given()
                .contentType("application/json")
                .pathParam("id", id)
                .body(request)
                .when().put("/produto/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Transactional
    public void testDeleteProdutoEndpoint() {
        Long id = 3L;

        given()
                .pathParam("id", id)
                .when().delete("/produto/{id}")
                .then()
                .statusCode(204);
    }
}
