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
public class JogadorApiTest {

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
    public void tc001_deveCriarJogadorComSucesso() {
        String payload = "{\n" +
                "  \"nome\": \"Neymar\",\n" +
                "  \"cpf\": \"12345678900\",\n" +
                "  \"idade\": 31,\n" +
                "  \"nacionalidade\": \"Brasil\",\n" +
                "  \"posicao\": \"Atacante\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/jogadores/adicionarJogador")
                .then()
                .statusCode(201);
    }

    @Test
    public void tc002_naoDeveCriarJogadorComPayloadInvalido() {
        // Faltando CPF e outros campos obrigatórios
        String payload = "{\n" +
                "  \"nome\": \"\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/jogadores/adicionarJogador")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    // ==========================================
    // BLOCO 2: GET (LISTAGEM E BUSCA)
    // ==========================================

    @Test
    public void tc003_deveListarJogadores() {
        given()
                .when()
                .get("/jogadores")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    @Test
    public void tc004_deveListarNomesDosJogadores() {
        given()
                .when()
                .get("/jogadores/nomes")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    @Test
    public void tc005_deveBuscarJogadorPorNome() {
        String nome = "Doku";

        given()
                .when()
                .get("/jogadores/" + nome)
                .then()
                .statusCode(is(200));
    }

    @Test
    public void tc006_naoDeveEncontrarJogadorInexistente() {
        given()
                .when()
                .get("/jogadores/JogadorFake123")
                .then()
                .statusCode(anyOf(is(404), is(500)));
    }

    // ==========================================
    // BLOCO 3: TESTES DE VALIDAÇÃO / ERROS
    // ==========================================

    @Test
    public void tc007_naoDevePermitirCriarJogadorComIdadeInvalida() {
        String payload = "{\n" +
                "  \"nome\": \"Teste\",\n" +
                "  \"cpf\": \"12345678900\",\n" +
                "  \"idade\": 0,\n" +
                "  \"nacionalidade\": \"Brasil\",\n" +
                "  \"posicao\": \"Atacante\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/jogadores/adicionarJogador")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc008_naoDevePermitirCriarJogadorComCamposVazios() {
        String payload = "{\n" +
                "  \"nome\": \"\",\n" +
                "  \"cpf\": \"\",\n" +
                "  \"idade\": 25,\n" +
                "  \"nacionalidade\": \"\",\n" +
                "  \"posicao\": \"\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/jogadores/adicionarJogador")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc009_deveBloquearMetodoGetNaRotaDeCriacao() {
        given()
                .when()
                .get("/jogadores/adicionarJogador")
                .then()
                .statusCode(anyOf(is(405), is(500)));
    }
}