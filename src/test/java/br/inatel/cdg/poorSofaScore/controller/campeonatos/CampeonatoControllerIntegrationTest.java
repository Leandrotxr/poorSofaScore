package br.inatel.cdg.poorSofaScore.controller.campeonatos;

import br.inatel.cdg.poorSofaScore.bussines.campeonatos.CampeonatoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoNomeDTO;
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

@WebMvcTest(CampeonatoController.class)
public class CampeonatoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampeonatoService campeonatoService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- TESTE GET /campeonato ----------
    @Test
    void deveListarCampeonato() throws Exception {
        CampeonatoDTO dto = CampeonatoDTO.builder().nome("Libertadores").build();
        when(campeonatoService.listarCampeonatos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/campeonato"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$[0].nome").value("Libertadores"));
    }

    // ---------- TESTE GET /campeonato/nomes ----------
    @Test
    void deveListarNomesDosCampeonatos() throws Exception {
        CampeonatoNomeDTO campeonatoNomeDTO = new CampeonatoNomeDTO("Libertadores");
        when(campeonatoService.listarNome()).thenReturn(List.of(campeonatoNomeDTO));

        mockMvc.perform(get("/campeonato/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Libertadores"));
    }

    // ---------- TESTE GET /campeonato/{nome} ----------
    @Test
    void deveBuscarCampeonatoPorNome() throws Exception {
        CampeonatoDTO dto = CampeonatoDTO.builder().nome("Libertadores").build();
        when(campeonatoService.buscarCampeonatoPorNome("Libertadores")).thenReturn(dto);

        mockMvc.perform(get("/campeonato/Libertadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Libertadores"));
    }

    // ---------- TESTE POST /campeonato/adicionarCampeonato ----------
    @Test
    void deveAdicionarArbitro() throws Exception {
        CampeonatoDTO dto = CampeonatoDTO.builder().nome("Libertadores").build();

        mockMvc.perform(post("/campeonato/adicionarCampeonato")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
