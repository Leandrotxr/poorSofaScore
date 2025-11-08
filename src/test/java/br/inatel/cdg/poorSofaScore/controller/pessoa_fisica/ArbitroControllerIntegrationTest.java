package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.ArbitroService;
import br.inatel.cdg.poorSofaScore.controller.pessoa_juridica.EquipeController;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroNomeDTO;
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

@WebMvcTest(ArbitroController.class)
public class ArbitroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArbitroService arbitroService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- TESTE GET /arbitro ----------
    @Test
    void deveListarArbitro() throws Exception {
        ArbitroDTO dto = ArbitroDTO.builder().nome("Daronco").build();
        when(arbitroService.listarArbitros()).thenReturn(List.of(dto));

        mockMvc.perform(get("/arbitro"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$[0].nome").value("Daronco"));
    }

    // ---------- TESTE GET /arbitro/nomes ----------
    @Test
    void deveListarNomesDosArbitros() throws Exception {
        ArbitroNomeDTO arbitroNomeDTO = new ArbitroNomeDTO("Daronco");
        when(arbitroService.listarNome()).thenReturn(List.of(arbitroNomeDTO));

        mockMvc.perform(get("/arbitro/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Daronco"));
    }

    // ---------- TESTE GET /arbitro/{nome} ----------
    @Test
    void deveBuscarArbitroPorNome() throws Exception {
        ArbitroDTO dto = ArbitroDTO.builder().nome("Daronco").build();
        when(arbitroService.buscarArbitroPorNome("Daronco")).thenReturn(dto);

        mockMvc.perform(get("/arbitro/Daronco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Daronco"));
    }

    // ---------- TESTE POST /arbitro/adicionarArbitro ----------
    @Test
    void deveAdicionarArbitro() throws Exception {
        ArbitroDTO dto = ArbitroDTO.builder().nome("Daronco").build();

        mockMvc.perform(post("/arbitro/adicionarArbitro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
