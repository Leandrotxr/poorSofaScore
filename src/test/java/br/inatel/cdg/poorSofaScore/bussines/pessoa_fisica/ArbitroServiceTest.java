package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.FederacaoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.ArbitroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArbitroServiceTest {

    @Mock
    private ArbitroRepository arbitroRepository;

    @InjectMocks
    private ArbitroService arbitroService;
    private FederacaoService federacaoService;

    private Arbitro arbitro;
    private Federacao federacao;

    private List<ArbitroDTO> executarListagemBasica() {
        when(arbitroRepository.findAll()).thenReturn(List.of(arbitro));
        return arbitroService.listarArbitros();
    }

    private ArbitroDTO arbitroDTO() {
        List<ArbitroDTO> resultado = executarListagemBasica();
        assertEquals(1, resultado.size());
        return resultado.get(0);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        arbitro = Arbitro.builder()
                .nome("Ramon Abatti Abel")
                .build();


        federacao = Federacao.builder()
                .nome("CBF")
                .lista_arbitro(List.of(arbitro))
                .build();
    }

    @Test
    public void deveAdicionarArbitroNaFederacao() {
        federacao.setLista_arbitro(new ArrayList<>());
        arbitro.contratar(federacao);
        assertTrue(federacao.getLista_arbitro().contains(arbitro));
    }

    @Test
    public void deveAdicionarFederacaoNoArbitro() {
        federacao.setLista_arbitro(new ArrayList<>());
        arbitro.contratar(federacao);
        assertEquals(arbitro.getFederacao(), federacao);
    }

    @Test
    public void deveListarApenasComNomesDosArbitros() {
        when(arbitroRepository.findAll()).thenReturn(List.of(arbitro));
        List<ArbitroNomeDTO> resultado = arbitroService.listarNome();
        assertEquals(1, resultado.size());
        assertEquals("Ramon Abatti Abel", resultado.get(0).getNome());
    }

    @Test
    void deveBuscarArbitroPorNomeComSucesso() {
        when(arbitroRepository.findByNome("Ramon Abatti Abel")).thenReturn(Optional.of(arbitro));

        ArbitroDTO dto = arbitroService.buscarArbitroPorNome("Ramon Abatti Abel");

        assertEquals("Ramon Abatti Abel", dto.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoArbitroNaoEncontrado() {
        when(arbitroRepository.findByNome("Inexistente")).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () ->
                arbitroService.buscarArbitroPorNome("Inexistente"));

        assertEquals("Arbitro não encontrado: Inexistente", excecao.getMessage());
    }

    @Test
    void deveSalvarArbitroNoRepositorio() {
        Arbitro arbitro = Arbitro.builder()
                .nome("Rafael Claus")
                .cpf("987654321")
                .idade(42)
                .build();

        when(arbitroRepository.save(any(Arbitro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        arbitroService.adicionarArbitro(arbitro);

        ArgumentCaptor<Arbitro> captor = ArgumentCaptor.forClass(Arbitro.class);
        verify(arbitroRepository, times(1)).save(captor.capture());

        Arbitro capturada = captor.getValue();

        assertEquals("Rafael Claus", capturada.getNome(), "O nome do árbitro deve ser 'Rafael Claus'");
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNuloOuVazio() {
        Arbitro arbitro = Arbitro.builder()
                .cpf("987654321")
                .idade(42)
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> arbitroService.adicionarArbitro(arbitro)
        );

        assertEquals("Nome do arbitro é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForNuloOuVazio() {
        Arbitro arbitro = Arbitro.builder()
                .nome("Rafael Claus")
                .idade(42)
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> arbitroService.adicionarArbitro(arbitro)
        );

        assertEquals("CPF do arbitro é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoIdadeForNuloOuVazio() {
        Arbitro arbitro = Arbitro.builder()
                .nome("Rafael Claus")
                .cpf("987654321")
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> arbitroService.adicionarArbitro(arbitro)
        );

        assertEquals("Idade do arbitro é obrigatório", ex.getMessage());
    }
}
