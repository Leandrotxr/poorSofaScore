package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.JogadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JogadorControllerTest {

    @Mock
    private JogadorService jogadorService;

    @InjectMocks
    private JogadorController jogadorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para GET /jogador
    @Test
    void deveListarJogador() {
        JogadorDTO jogadorDTO = JogadorDTO.builder().nome("Kaio Jorge").build();
        when(jogadorService.listarJogadores()).thenReturn(List.of(jogadorDTO));

        List<JogadorDTO> resultado = jogadorController.listarJogadores();

        assertEquals(1, resultado.size());
        assertEquals("Kaio Jorge", resultado.get(0).getNome());
        verify(jogadorService, times(1)).listarJogadores();
    }

    // Teste para GET /jogador/nomes
    @Test
    void deveListarNomesDosJogadores() {
        JogadorNomeDTO jogadorNomeDTO = new JogadorNomeDTO("Kaio Jorge");
        when(jogadorService.listarNome()).thenReturn(List.of(jogadorNomeDTO));

        List<JogadorNomeDTO> resultado = jogadorController.listarNomes();

        assertEquals("Kaio Jorge", resultado.get(0).getNome());
        verify(jogadorService, times(1)).listarNome();
    }

    // Teste para GET /jogador/{nome}
    @Test
    void deveBuscarJogadorPorNome() {
        JogadorDTO jogadorDTO = JogadorDTO.builder().nome("Kaio Jorge").build();
        when(jogadorService.buscarJogadorPorNome("Kaio Jorge")).thenReturn(jogadorDTO);

        JogadorDTO resultado = jogadorController.buscarJogadorPorNome("Kaio Jorge");

        assertEquals("Kaio Jorge", resultado.getNome());
        verify(jogadorService, times(1)).buscarJogadorPorNome("Kaio Jorge");
    }

    // Teste para POST /jogador/adicionarJogador
    @Test
    void deveAdicionarArbitro() {
        Jogador jogador = new Jogador();

        jogadorController.adicionarJogador(jogador);

        verify(jogadorService, times(1)).adicionarJogador(jogador);
    }
}
