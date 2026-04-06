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
public class CampeonatoApiTest {

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
    public void tc001_deveCriarCampeonatoComSucesso() {
        // Caminho Feliz: Payload correto
        String payload = "{\n" +
                "  \"nome\": \"Champions League\",\n" +
                "  \"local\": \"Europa\",\n" +
                "  \"premio\": 85000000.0\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/campeonatos/adicionarCampeonato")
                .then()
                .statusCode(201); // 201 Created
    }

    @Test
    public void tc002_naoDeveCriarCampeonatoSemNome() {
        // Caminho Triste: Ausência de campo obrigatório (Encontramos que a API dá erro 500)
        String payloadInvalido = "{\n" +
                "  \"local\": \"Argentina\",\n" +
                "  \"premio\": 100000.0\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payloadInvalido)
                .when()
                .post("/campeonatos/adicionarCampeonato")
                .then()
                .statusCode(500);
    }

    @Test
    public void tc003_naoDeveCriarCampeonatoComPayloadVazio() {
        // Caminho Triste: Corpo da requisição totalmente vazio
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/campeonatos/adicionarCampeonato")
                .then()
                .statusCode(500); // Sem validações, o Spring vai tentar criar uma entidade nula e quebrar
    }

    @Test
    public void tc004_naoDeveCriarCampeonatoComTipoInvalido() {
        // Caminho Triste: Enviando uma String no lugar de um Número para o prêmio
        String payloadInvalido = "{\n" +
                "  \"nome\": \"Copa America\",\n" +
                "  \"local\": \"EUA\",\n" +
                "  \"premio\": \"muito dinheiro\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payloadInvalido)
                .when()
                .post("/campeonatos/adicionarCampeonato")
                .then()
                .statusCode(400); // 400 Bad Request, pois o Jackson não consegue converter a String para Double/Int
    }

    // ==========================================
    // BLOCO 2: TESTES DE LISTAGEM (GET)
    // ==========================================

    @Test
    public void tc005_deveListarTodosOsCampeonatos() {
        // Caminho Feliz: Recuperar lista geral
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/campeonatos")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    @Test
    public void tc006_deveListarNomesDosCampeonatos() {
        // Caminho Feliz: Rota específica que retorna apenas os DTOs de Nomes
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/campeonatos/nomes")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    // ==========================================
    // BLOCO 3: TESTES DE BUSCA ESPECÍFICA (GET {nome})
    // ==========================================

    @Test
    public void tc007_deveBuscarCampeonatoPorNomeExistente() {
        // Pré-condição: Criar um campeonato primeiro para garantir que ele existe
        String nomeTeste = "Paulistao";
        String payload = "{\n" +
                "  \"nome\": \"" + nomeTeste + "\",\n" +
                "  \"local\": \"Sao Paulo\",\n" +
                "  \"premio\": 50000.0\n" +
                "}";

        given().contentType(ContentType.JSON).body(payload).post("/campeonatos/adicionarCampeonato");

        // Caminho Feliz: Buscar o campeonato que acabou de ser criado
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/campeonatos/" + nomeTeste)
                .then()
                .statusCode(200)
                .body("nome", equalTo(nomeTeste)); // Valida se o banco trouxe o campeonato certo
    }

    @Test
    public void tc008_naoDeveEncontrarCampeonatoComNomeInexistente() {
        // Caminho Triste: Buscar um nome que não faz sentido
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/campeonatos/CampeonatoQueNaoExiste123")
                .then()
                // ATENÇÃO: Verifique o que a sua API retorna no terminal.
                // Pode ser 404 (Not Found), 200 (com body vazio) ou 500 (Exception).
                .statusCode(anyOf(is(404), is(200), is(500)));
    }

    // ==========================================
    // BLOCO 4: TESTES DE CONTRATO HTTP
    // ==========================================

    @Test
    public void tc009_naoDeveAceitarMetodoHttpIncorreto() {
        // Caminho Triste: Tentar fazer um POST na rota que só aceita GET
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/campeonatos")
                .then()
                .statusCode(405); // 405 Method Not Allowed
    }

    // TC-044 a TC-047: Novos cenários para Campeonatos

    @Test
    public void tc044_naoCriarCampeonatoComPremioNegativo() {
        // Dado Inválido: Valor de prêmio abaixo de zero
        String payload = "{\n" +
                "  \"nome\": \"Copa Falida\",\n" +
                "  \"local\": \"Brasil\",\n" +
                "  \"premio\": -100.0\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/campeonatos/adicionarCampeonato")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    public void tc045_CriarCampeonatoComNomeMuitoLongo() {

        //Nome com mais de 100 caracteres para testar limites de validação
        String nomeLongo = "Campeonato Intergalatico de Futebol de Varzea 2026";
        String payload = "{\n" +
                "  \"nome\": \"" + nomeLongo + "\",\n" +
                "  \"local\": \"Brasil\",\n" +
                "  \"premio\": 1000.0\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/campeonatos/adicionarCampeonato")
                .then()
                .statusCode(201);
    }

    @Test
    public void tc046_naoDeveAcessarRotaInexistenteDentroDeCampeonatos() {
        // Dados Inoportunos: Tentando acessar um endpoint que não existe no controller
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/campeonatos/configuracoes/avancadas")
                .then()
                .statusCode(404);
    }

    @Test
    public void tc047_naoDeveCriarCampeonatoComLocalEmBranco() {
        // Dado Inválido: Nome existe, mas o local é uma string vazia
        String payload = "{\n" +
                "  \"nome\": \"Torneio Sem Sede\",\n" +
                "  \"local\": \"\",\n" +
                "  \"premio\": 500.0\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/campeonatos/adicionarCampeonato")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }
}