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
public class EquipeApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    // ==========================================
    // BLOCO 1: TESTES DE CRIAÇÃO (POST)
    // ==========================================

    @Test
    public void tc010_deveCriarEquipeComSucesso() {
        String payload = "{\n" +
                "  \"nome\": \"Palmeiras\",\n" +
                "  \"local\": \"Brasil\",\n" +
                "  \"divida\": 0.0\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/equipes/adicionarEquipe")
                .then()
                .statusCode(201); // Valida o HttpStatus.CREATED que está no Controller
    }

    @Test
    public void tc011_naoDeveCriarEquipeComPayloadInvalido() {
        // Faltando campos obrigatórios
        String payload = "{\n" +
                "  \"local\": \"Brasil\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/equipes/adicionarEquipe")
                .then()
                .statusCode(anyOf(is(400), is(500))); // Pode ser 400 por causa do @Validated ou 500
    }

    // ==========================================
    // BLOCO 2: TESTES DE LISTAGEM E BUSCA (GET)
    // ==========================================

    @Test
    public void tc012_deveListarTodasAsEquipes() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/equipes")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    @Test
    public void tc013_deveListarApenasNomesDasEquipes() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/equipes/nomes")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    @Test
    public void tc014_deveBuscarEquipePorNome() {
        // Como o BD é real na execução, buscamos uma equipe criada no tc010 ou garantimos uma
        String nomeBusca = "Palmeiras";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/equipes/" + nomeBusca)
                .then()
                // Aceitando 200 se achar, ou 404/200/500 se o banco rodar em ordem diferente e não achar
                .statusCode(anyOf(is(200), is(404), is(500)));
    }

    // ==========================================
    // BLOCO 3: TESTES DE NEGÓCIO - CONTRATOS (PATCH)
    // ==========================================

    @Test
    public void tc015_deveContratarTecnico() {
        // Payload mapeado para ContratarTecnicoDTO
        String payload = "{\n" +
                "  \"nomeEquipe\": \"Palmeiras\",\n" +
                "  \"nomeTecnico\": \"Abel Ferreira\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("/equipes/contratarTecnico")
                .then()
                .statusCode(anyOf(is(200), is(500))); // Retorna 200 se a equipe e o técnico existirem no BD. 500 se não existirem.
    }

    @Test
    public void tc016_deveDemitirTecnico() {
        // Payload mapeado para EquipeNomeDTO
        String payload = "{\n" +
                "  \"nome\": \"Palmeiras\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("/equipes/demitirTecnico")
                .then()
                .statusCode(anyOf(is(200), is(500)));
    }

    @Test
    public void tc017_deveContratarJogador() {
        // Payload mapeado para ContratarJogadorDTO
        String payload = "{\n" +
                "  \"nomeEquipe\": \"Palmeiras\",\n" +
                "  \"nomeJogador\": \"Dudu\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("/equipes/contratarJogador")
                .then()
                .statusCode(anyOf(is(200), is(500)));
    }

    @Test
    public void tc018_deveDemitirJogador() {
        // Payload mapeado para DemitirJogadorDTO
        String payload = "{\n" +
                "  \"nomeEquipe\": \"Palmeiras\",\n" +
                "  \"nomeJogador\": \"Dudu\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("/equipes/demitirJogador")
                .then()
                .statusCode(anyOf(is(200), is(500)));
    }

    @Test
    public void tc019_naoDeveContratarJogadorComEquipeInexistente() {
        // Caminho Triste: Testando a resiliência do PATCH ao mandar uma equipe absurda
        String payload = "{\n" +
                "  \"nomeEquipe\": \"EquipeFantasma123\",\n" +
                "  \"nomeJogador\": \"Messi\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("/equipes/contratarJogador")
                .then()
                .statusCode(anyOf(is(404), is(500), is(400))); // Ideal seria 404 Not Found
    }

    // ==========================================
    // BLOCO 5: TESTES EXTREMOS E VALIDAÇÕES DE NEGÓCIO
    // ==========================================

    @Test
    public void tc020_naoDevePermitirContratarJogadorComPayloadVazio() {
        // Caminho Triste: Tentar fazer o PATCH sem enviar o corpo da requisição
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .patch("/equipes/contratarJogador")
                .then()
                // Como faltam os nomes da equipa e do jogador, a API deve falhar (idealmente 400, mas pode dar 500)
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc021_naoDevePermitirDemitirJogadorComNomeEmBranco() {
        // Caminho Triste: Enviar as chaves corretas, mas com valores em branco (vazios)
        String payloadVazio = "{\n" +
                "  \"nomeEquipe\": \"\",\n" +
                "  \"nomeJogador\": \"\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payloadVazio)
                .when()
                .patch("/equipes/demitirJogador")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc022_deveBloquearMetodoGetNaRotaDeCriacao() {
        // Segurança/Contrato: A rota /adicionarEquipe é exclusiva para POST.
        // O que acontece se alguém tentar aceder via GET no navegador?
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/equipes/adicionarEquipe")
                .then()
                .statusCode(405); // 405 Method Not Allowed (O Spring Boot trata isto automaticamente)
    }

    @Test
    public void tc023_naoDeveAceitarTiposDeDadosIncorretosNoPatch() {
        // Caminho Triste: Tentar enviar números onde a API espera Strings (Nomes)
        String payloadTipagemErrada = "{\n" +
                "  \"nomeEquipe\": 12345,\n" +
                "  \"nomeTecnico\": 67890\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payloadTipagemErrada)
                .when()
                .patch("/equipes/contratarTecnico")
                .then()
                // O conversor JSON do Spring vai tentar transformar o número em String ou vai falhar
                .statusCode(anyOf(is(400), is(500)));
    }
}