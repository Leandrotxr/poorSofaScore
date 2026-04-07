package br.inatel.cdg.poorSofaScore.api;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TecnicoApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    // ==========================================
    // BLOCO 1: CRIAÇÃO (POST)
    // ==========================================

    @Test
    public void tc001_deveCriarTecnicoComSucesso() {
        String payload = "{\n" +
                "  \"nome\": \"Klopp\",\n" +
                "  \"cpf\": \"12345678900\",\n" +
                "  \"idade\": 58,\n" +
                "  \"nacionalidade\": \"Alemanha\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/tecnicos/adicionarTecnico")
                .then()
                .statusCode(201);
    }

    @Test
    public void tc002_naoDeveCriarTecnicoComPayloadInvalido() {
        // Faltando CPF e outros campos obrigatórios
        String payload = "{\n" +
                "  \"nome\": \"\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/tecnicos/adicionarTecnico")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    // ==========================================
    // BLOCO 2: GET (LISTAGEM E BUSCA)
    // ==========================================

    @Test
    public void tc003_deveListarTecnicos() {
        given()
                .when()
                .get("/tecnicos")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    @Test
    public void tc004_deveListarNomesDosTecnicos() {
        given()
                .when()
                .get("/tecnicos/nomes")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    @Test
    public void tc005_deveBuscarTecnicoPorNome() {
        String nome = "Guardiola";

        given()
                .when()
                .get("/tecnicos/" + nome)
                .then()
                .statusCode(is(200));
    }

    @Test
    public void tc006_naoDeveEncontrarTecnicoInexistente() {
        given()
                .when()
                .get("/tecnicos/tecnicoFake")
                .then()
                .statusCode(anyOf(is(404), is(500)));
    }

    // ==========================================
    // BLOCO 3: TESTES DE VALIDAÇÃO / ERROS
    // ==========================================

    @Test
    public void tc007_naoDevePermitirCriarTecnicoComIdadeInvalida() {
        String payload = "{\n" +
                "  \"nome\": \"Klopp\",\n" +
                "  \"cpf\": \"12345678900\",\n" +
                "  \"idade\": 0,\n" +
                "  \"nacionalidade\": \"Alemanha\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/tecnicos/adicionarTecnico")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc008_naoDevePermitirCriarTecnicoComCamposVazios() {
        String payload = "{\n" +
                "  \"nome\": \"\",\n" +
                "  \"cpf\": \"\",\n" +
                "  \"idade\": 40,\n" +
                "  \"nacionalidade\": \"\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/tecnicos/adicionarTecnico")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc009_naoDeveAceitarTiposDeDadosIncorretosNoPost() {
        String payload = "{\n" +
                "  \"nome\": 123,\n" +
                "  \"cpf\": 12345678900,\n" +
                "  \"idade\": \"40\",\n" +
                "  \"nacionalidade\": \"\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/tecnicos/adicionarTecnico")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc010_deveBloquearMetodoGetNaRotaDeCriacao() {
        given()
                .when()
                .get("/tecnicos/adicionarTecnico")
                .then()
                .statusCode(anyOf(is(405), is(500)));
    }
}
