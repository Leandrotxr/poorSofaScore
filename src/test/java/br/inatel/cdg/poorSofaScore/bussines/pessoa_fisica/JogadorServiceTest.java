package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.JogadorRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JogadorServiceTest {

    @Mock
    private JogadorRepository jogadorRepository;

    @InjectMocks
    private JogadorService jogadorService;

    @Test
    void deveSalvarNovoJogador() {

        Jogador novoJogador = Jogador.builder()
                .nome("Neymar Jr")
                .idade(31)
                .nacionalidade("Brasileiro")
                .posicao("Atacante")
                .build();

        jogadorService.adicionarJogador(novoJogador);

        verify(jogadorRepository, times(1)).save(novoJogador);
    }

    @Test
    void deveRetornarListaDeJogadoresDTO() {

        Jogador jogador1 = Jogador.builder()
                .nome("Messi")
                .idade(36)
                .nacionalidade("Argentino")
                .posicao("Atacante")
                .build();

        Jogador jogador2 = Jogador.builder()
                .nome("Casemiro")
                .idade(32)
                .nacionalidade("Brasileiro")
                .posicao("Volante")
                .build();

        List<Jogador> lista = new ArrayList<>();
        lista.add(jogador1);
        lista.add(jogador2);

        when(jogadorRepository.findAll()).thenReturn(lista);

        List<JogadorDTO> resultado = jogadorService.listarJogadores();

        verify(jogadorRepository, times(1)).findAll();
        assertEquals(2, resultado.size());
        assertEquals("Messi", resultado.get(0).getNome());
        assertEquals("Casemiro", resultado.get(1).getNome());
    }

    @Test
    void deveRetornarJogadorDTOQuandoBuscarPorNome() {

        String nomeBusca = "Neymar";

        Jogador jogador = Jogador.builder()
                .nome(nomeBusca)
                .idade(31)
                .nacionalidade("Brasileiro")
                .posicao("Atacante")
                .build();

        when(jogadorRepository.findByNome(nomeBusca)).thenReturn(Optional.of(jogador));

        JogadorDTO resultado = jogadorService.buscarJogadorPorNome(nomeBusca);

        verify(jogadorRepository, times(1)).findByNome(nomeBusca);
        assertNotNull(resultado);
        assertEquals(nomeBusca, resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoJogadorNaoExistir() {

        String nomeInexistente = "Jogador X";

        when(jogadorRepository.findByNome(nomeInexistente))
                .thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            jogadorService.buscarJogadorPorNome(nomeInexistente);
        });

        assertTrue(excecao.getMessage().contains("Jogador não encontrada: " + nomeInexistente));
        verify(jogadorRepository, times(1)).findByNome(nomeInexistente);
    }
}
