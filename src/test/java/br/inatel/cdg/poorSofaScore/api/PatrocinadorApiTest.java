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

    @Test
    public void tc004_naoDeveCriarPatrocinadorComInvestimentoNegativo() {
        // Dado Inválido: Teste de limite financeiro
        String payload = "{\n" +
                "  \"nome\": \"Patrocinio Negativo\",\n" +
                "  \"cnpj\": \"00000000000100\",\n" +
                "  \"investimento\": -1000.0\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/patrocinadores/adicionarPatrocinador")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc005_deveRetornarErroAoBuscarPatrocinadorInexistente() {
        // Dado Inoportuno: Busca por um recurso que não consta no banco 
        String nomeEmpresa = "EmpresaInexistente999";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/patrocinadores/" + nomeEmpresa)
                .then()
                .statusCode(anyOf(is(404), is(500)));
    }

    @Test
    public void tc006_naoDeveAceitarTipagemIncorretaNoInvestimento() {
        // Dado Inválido: Enviando uma String ("milhoes") em um campo que espera Double
        String payload = "{\n" +
                "  \"nome\": \"Patrocinador Erro Tipo\",\n" +
                "  \"cnpj\": \"11222333000188\",\n" +
                "  \"investimento\": \"dez mil reais\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/patrocinadores/adicionarPatrocinador")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

     @Test
    public void tc007_naoDeveCriarPatrocinadorSemCnpj() {
        // Caminho Triste: Payload sem o campo CNPJ, que é obrigatório pelo service
        String payload = "{\n" +
                "  \"nome\": \"Patrocinador Sem CNPJ\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/patrocinadores/adicionarPatrocinador")
                .then()
                .statusCode(anyOf(is(400), is(500))); // O service lança IllegalArgumentException se o CNPJ for nulo
    }

    @Test
    public void tc008_naoDeveCriarPatrocinadorComPayloadVazio() {
        // Caminho Triste: Enviar um JSON completamente vazio, sem nome nem CNPJ
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/patrocinadores/adicionarPatrocinador")
                .then()
                .statusCode(anyOf(is(400), is(500))); // Deve falhar pois ambos os campos são obrigatórios
    }

}
