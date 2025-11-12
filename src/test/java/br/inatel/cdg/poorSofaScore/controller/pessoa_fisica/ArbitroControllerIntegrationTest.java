package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.ArbitroService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
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

    // ---------- TESTE GET /arbitros ----------
    @Test
    void deveListarArbitro() throws Exception {
        ArbitroDTO dto = ArbitroDTO.builder().nome("Daronco").build();
        when(arbitroService.listarArbitros()).thenReturn(List.of(dto));

        mockMvc.perform(get("/arbitros"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$[0].nome").value("Daronco"));
    }

    // ---------- TESTE GET /arbitros/nomes ----------
    @Test
    void deveListarNomesDosArbitros() throws Exception {
        ArbitroNomeDTO arbitroNomeDTO = new ArbitroNomeDTO("Daronco");
        when(arbitroService.listarNome()).thenReturn(List.of(arbitroNomeDTO));

        mockMvc.perform(get("/arbitros/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Daronco"));
    }

    // ---------- TESTE GET /arbitros/{nome} ----------
    @Test
    void deveBuscarArbitroPorNome() throws Exception {
        ArbitroDTO dto = ArbitroDTO.builder().nome("Daronco").build();
        when(arbitroService.buscarArbitroPorNome("Daronco")).thenReturn(dto);

        mockMvc.perform(get("/arbitros/Daronco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Daronco"));
    }

    // ---------- TESTE POST /arbitros/adicionarArbitro ----------
    @Test
    void deveAdicionarArbitro() throws Exception {
        Arbitro dto = Arbitro.builder()
                .nome("Daronco")
                .cpf("123456789")
                .idade(45)
                .build();

        mockMvc.perform(post("/arbitros/adicionarArbitro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
