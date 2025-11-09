package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.TecnicoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoNomeDTO;
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

@WebMvcTest(TecnicoController.class)
public class TecnicoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TecnicoService tecnicoService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- TESTE GET /tecnico ----------
    @Test
    void deveListarTecnico() throws Exception {
        TecnicoDTO dto = TecnicoDTO.builder().nome("Guardiola").build();
        when(tecnicoService.listarTecnicos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/tecnico"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$[0].nome").value("Guardiola"));
    }

    // ---------- TESTE GET /jogador/nomes ----------
    @Test
    void deveListarNomesDosTecnicos() throws Exception {
        TecnicoNomeDTO tecnicoNomeDTO = new TecnicoNomeDTO("Guardiola");
        when(tecnicoService.listarNome()).thenReturn(List.of(tecnicoNomeDTO));

        mockMvc.perform(get("/tecnico/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Guardiola"));
    }

    // ---------- TESTE GET /tecnico/{nome} ----------
    @Test
    void deveBuscarTecnicoPorNome() throws Exception {
        TecnicoDTO dto = TecnicoDTO.builder().nome("Guardiola").build();
        when(tecnicoService.buscarTecnicoPorNome("Guardiola")).thenReturn(dto);

        mockMvc.perform(get("/tecnico/Guardiola"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Guardiola"));
    }

    // ---------- TESTE POST /jogador/adicionarJogador ----------
    @Test
    void deveAdicionarJogador() throws Exception {
        TecnicoDTO dto = TecnicoDTO.builder().nome("Guardiola").build();

        mockMvc.perform(post("/tecnico/adicionarTecnico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
