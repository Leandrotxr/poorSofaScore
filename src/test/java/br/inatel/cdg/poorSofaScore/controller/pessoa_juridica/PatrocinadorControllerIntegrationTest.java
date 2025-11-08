package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.PatrocinadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorNomeDTO;
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

@WebMvcTest(PatrocinadorController.class)
public class PatrocinadorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatrocinadorService patrocinadorService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- TESTE GET /patrocinador/nomes ----------
    @Test
    void deveListarNomesDosPatrocinadores() throws Exception {
        PatrocinadorNomeDTO nomeDTO = new PatrocinadorNomeDTO("Puma");
        when(patrocinadorService.listarNome()).thenReturn(List.of(nomeDTO));

        mockMvc.perform(get("/patrocinador/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Puma"));
    }

    // ---------- TESTE POST /patrocinador/adicionarPatrocinador ----------
    @Test
    void deveAdicionarPatrocinador() throws Exception {
        PatrocinadorDTO dto = PatrocinadorDTO.builder().nome("Puma").build();

        mockMvc.perform(post("/patrocinador/adicionarPatrocinador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
