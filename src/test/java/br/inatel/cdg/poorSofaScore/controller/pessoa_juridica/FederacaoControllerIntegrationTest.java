package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.FederacaoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoNomeDTO;
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

    // ---------- TESTE GET /federacao ----------
    @Test
    void deveListarFederacao() throws Exception {
        FederacaoDTO dto = FederacaoDTO.builder().nome("Fifa").build();
        when(federacaoService.listarFederacao()).thenReturn(List.of(dto));

        mockMvc.perform(get("/federacao"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$[0].nome").value("Fifa"));
    }

    // ---------- TESTE GET /federacao/nomes ----------
    @Test
    void deveListarNomesDasFederacoes() throws Exception {
        FederacaoNomeDTO nomeDTO = new FederacaoNomeDTO("Fifa");
        when(federacaoService.listarNome()).thenReturn(List.of(nomeDTO));

        mockMvc.perform(get("/federacao/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Fifa"));
    }

    // ---------- TESTE GET /federacao/{nome} ----------
    @Test
    void deveBuscarFederacaoPorNome() throws Exception {
        FederacaoDTO dto = FederacaoDTO.builder().nome("Fifa").build();
        when(federacaoService.buscarFederacaoPorNome("Fifa")).thenReturn(dto);

        mockMvc.perform(get("/federacao/Fifa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Fifa"));
    }

    // ---------- TESTE POST /federacao/adicionarFederacao ----------
    @Test
    void deveAdicionarFederacao() throws Exception {
        FederacaoDTO dto = FederacaoDTO.builder().nome("Fifa").build();

        mockMvc.perform(post("/federacao/adicionarFederacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
