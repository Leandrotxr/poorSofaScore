package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.JogadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JogadorServiceTest {

    @Mock
    private JogadorRepository jogadorRepository;

    @InjectMocks
    private JogadorService jogadorService;

    private Jogador jogador;
    private Equipe equipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        equipe = Equipe.builder()
                .nome("Barcelona")
                .build();

        jogador = Jogador.builder()
                .nome("Messi")
                .idade(35)
                .cpf("999")
                .nacionalidade("Argentina")
                .posicao("Atacante")
                .equipe(equipe)
                .build();
    }

    private List<JogadorDTO> executarListagemBasica() {
        when(jogadorRepository.findAll()).thenReturn(List.of(jogador));
        return jogadorService.listarJogadores();
    }

    private JogadorDTO jogadorDTO() {
        List<JogadorDTO> resultado = executarListagemBasica();
        assertEquals(1, resultado.size());
        return resultado.get(0);
    }

    // ---------------------
    // TESTES DE LISTAGEM
    // ---------------------

    @Test
    void deveListarJogadoresComNomeConvertidoParaDTO() {
        assertEquals("Messi", jogadorDTO().getNome());
    }

    @Test
    void deveListarJogadoresComIdadeConvertidaParaDTO() {
        assertEquals(35, jogadorDTO().getIdade());
    }

    @Test
    void deveListarJogadoresComNacionalidadeConvertidaParaDTO() {
        assertEquals("Argentina", jogadorDTO().getNacionalidade());
    }

    @Test
    void deveListarJogadoresComPosicaoConvertidaParaDTO() {
        assertEquals("Atacante", jogadorDTO().getPosicao());
    }

    @Test
    void deveListarJogadoresComEquipeConvertidaParaDTO() {
        assertEquals("Barcelona", jogadorDTO().getEquipe());
    }

    @Test
    void deveListarSomenteNomesDosJogadores() {
        when(jogadorRepository.findAll()).thenReturn(List.of(jogador));

        List<JogadorNomeDTO> nomes = jogadorService.listarNome();

        assertEquals(1, nomes.size());
        assertEquals("Messi", nomes.get(0).getNome());
    }

    // ---------------------
    // TESTES DE BUSCA POR NOME
    // ---------------------

    @Test
    void deveBuscarJogadorPorNomeComSucesso() {
        when(jogadorRepository.findByNome("Messi")).thenReturn(Optional.of(jogador));

        JogadorDTO dto = jogadorService.buscarJogadorPorNome("Messi");

        assertEquals("Messi", dto.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoJogadorNaoEncontrado() {
        when(jogadorRepository.findByNome("Inexistente")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> jogadorService.buscarJogadorPorNome("Inexistente"));

        assertEquals("Jogador não encontrada: Inexistente", ex.getMessage());
    }

    // ---------------------
    // TESTES DE ADIÇÃO
    // ---------------------

    @Test
    void deveSalvarJogadorNoRepositorio() {
        when(jogadorRepository.save(any(Jogador.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        jogadorService.adicionarJogador(jogador);

        ArgumentCaptor<Jogador> captor = ArgumentCaptor.forClass(Jogador.class);
        verify(jogadorRepository).save(captor.capture());

        assertEquals("Messi", captor.getValue().getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForInvalido() {
        Jogador j = Jogador.builder()
                .cpf("123")
                .idade(20)
                .nacionalidade("Brasil")
                .posicao("Zagueiro")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> jogadorService.adicionarJogador(j));

        assertEquals("Nome do jogador é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        Jogador j = Jogador.builder()
                .nome("Teste")
                .idade(20)
                .nacionalidade("Brasil")
                .posicao("Zagueiro")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> jogadorService.adicionarJogador(j));

        assertEquals("CPF do jogador é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoIdadeForInvalida() {
        Jogador j = Jogador.builder()
                .nome("Teste")
                .cpf("123")
                .idade(0)
                .nacionalidade("Brasil")
                .posicao("Zagueiro")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> jogadorService.adicionarJogador(j));

        assertEquals("Idade do jogador é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNacionalidadeForInvalida() {
        Jogador j = Jogador.builder()
                .nome("Teste")
                .cpf("123")
                .idade(20)
                .posicao("Zagueiro")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> jogadorService.adicionarJogador(j));

        assertEquals("Nacionalidade do jogador é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPosicaoForInvalida() {
        Jogador j = Jogador.builder()
                .nome("Teste")
                .cpf("123")
                .idade(20)
                .nacionalidade("Brasil")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> jogadorService.adicionarJogador(j));

        assertEquals("Nacionalidade do jogador é obrigatório", ex.getMessage());
    }
}
