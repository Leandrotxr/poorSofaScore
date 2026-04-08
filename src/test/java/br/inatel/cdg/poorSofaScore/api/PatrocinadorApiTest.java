package br.inatel.cdg.poorSofaScore.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatrocinadorApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void tc001_deveCriarPatrocinadorComSucesso() {
        String payload = "{\n" +
                "  \"nome\": \"Unicef\",\n" +
                "  \"cnpj\": \"12345678123456\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/patrocinadores/adicionarPatrocinador")
                .then()
                .statusCode(201); // 201 Created
    }

    @Test
    public void tc002_naoDeveCriarPatrocinadorSemNome() {
        String payload = "{\n" +
                "  \"cnpj\": \"12345678123456\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/campeonatos/adicionarCampeonato")
                .then()
                .statusCode(500);
    }

    @Test
    public void tc003_deveListarNomesDosPatrocinadores() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/patrocinadores/nomes")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }
}
