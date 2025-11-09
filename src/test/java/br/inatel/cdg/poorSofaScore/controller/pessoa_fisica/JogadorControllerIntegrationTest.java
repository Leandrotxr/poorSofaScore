package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.JogadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
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

@WebMvcTest(JogadorController.class)
public class JogadorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JogadorService jogadorService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- TESTE GET /jogador ----------
    @Test
    void deveListarJogador() throws Exception {
        JogadorDTO dto = JogadorDTO.builder().nome("Messi").build();
        when(jogadorService.listarJogadores()).thenReturn(List.of(dto));

        mockMvc.perform(get("/jogador"))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$[0].nome").value("Messi"));
    }

    // ---------- TESTE GET /jogador/nomes ----------
    @Test
    void deveListarNomesDosJogadores() throws Exception {
        JogadorNomeDTO jogadorNomeDTO = new JogadorNomeDTO("Messi");
        when(jogadorService.listarNome()).thenReturn(List.of(jogadorNomeDTO));

        mockMvc.perform(get("/jogador/nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Messi"));
    }

    // ---------- TESTE GET /jogador/{nome} ----------
    @Test
    void deveBuscarJogadorPorNome() throws Exception {
        JogadorDTO dto = JogadorDTO.builder().nome("Messi").build();
        when(jogadorService.buscarJogadorPorNome("Messi")).thenReturn(dto);

        mockMvc.perform(get("/jogador/Messi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Messi"));
    }

    // ---------- TESTE POST /jogador/adicionarJogador ----------
    @Test
    void deveAdicionarJogador() throws Exception {
        JogadorDTO dto = JogadorDTO.builder().nome("Messi").build();

        mockMvc.perform(post("/jogador/adicionarJogador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
