package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.FederacaoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FederacaoController.class)
public class FederacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FederacaoService federacaoService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- TESTE GET /federacoes ----------
    @Test
    void deveListarFederacao() throws Exception {
        FederacaoDTO dto = FederacaoDTO.builder().nome("Fifa").build();
        when(federacaoService.listarFederacao()).thenReturn(List.of(dto));

        mockMvc.perform(get("/federacoes"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$[0].nome").value("Fifa"));
    }

    // ---------- TESTE GET /federacoes/nomes ----------
    @Test
    void deveListarNomesDasFederacoes() throws Exception {
        FederacaoNomeDTO nomeDTO = new FederacaoNomeDTO("Fifa");
        when(federacaoService.listarNome()).thenReturn(List.of(nomeDTO));

        mockMvc.perform(get("/federacoes/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Fifa"));
    }

    // ---------- TESTE GET /federacoes/{nome} ----------
    @Test
    void deveBuscarFederacaoPorNome() throws Exception {
        FederacaoDTO dto = FederacaoDTO.builder().nome("Fifa").build();
        when(federacaoService.buscarFederacaoPorNome("Fifa")).thenReturn(dto);

        mockMvc.perform(get("/federacoes/Fifa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Fifa"));
    }

    // ---------- TESTE POST /federacoes/adicionarFederacao ----------
    @Test
    void deveAdicionarFederacao() throws Exception {
        Federacao dto = Federacao.builder()
                .nome("Fifa")
                .cnpj("1234")
                .build();

        mockMvc.perform(post("/federacoes/adicionarFederacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
