package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.EquipeService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
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

@WebMvcTest(EquipeController.class)
class EquipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipeService equipeService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- TESTE GET /equipes ----------
    @Test
    void deveListarEquipes() throws Exception {
        EquipeDTO dto = EquipeDTO.builder().nome("Flamengo").sede("RJ").build();
        when(equipeService.listarEquipes()).thenReturn(List.of(dto));

        mockMvc.perform(get("/equipes"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$[0].nome").value("Flamengo"))
                .andExpect(jsonPath("$[0].sede").value("RJ"));
    }

    // ---------- TESTE GET /equipes/nomes ----------
    @Test
    void deveListarNomesDasEquipes() throws Exception {
        EquipeNomeDTO nomeDTO = new EquipeNomeDTO("Corinthians");
        when(equipeService.listarNomes()).thenReturn(List.of(nomeDTO));

        mockMvc.perform(get("/equipes/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corinthians"));
    }

    // ---------- TESTE GET /equipes/{nome} ----------
    @Test
    void deveBuscarEquipePorNome() throws Exception {
        EquipeDTO dto = EquipeDTO.builder().nome("Flamengo").sede("RJ").build();
        when(equipeService.buscarEquipePorNome("Flamengo")).thenReturn(dto);

        mockMvc.perform(get("/equipes/Flamengo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Flamengo"))
                .andExpect(jsonPath("$.sede").value("RJ"));
    }

    // ---------- TESTE POST /equipes/adicionarEquipe ----------
    @Test
    void deveAdicionarEquipe() throws Exception {
        Equipe dto = Equipe.builder()
                .nome("Flamengo")
                .cnpj("1234")
                .fundacao(1900)
                .sede("RJ")
                .build();

        mockMvc.perform(post("/equipes/adicionarEquipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}