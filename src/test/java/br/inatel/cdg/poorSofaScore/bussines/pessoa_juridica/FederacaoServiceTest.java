package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.FederacaoRepository;
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
import static org.mockito.Mockito.*;

public class FederacaoServiceTest {

    @Mock
    private FederacaoRepository federacaoRepository;

    @InjectMocks
    private FederacaoService federacaoService;

    private Federacao federacao;
    private Campeonato campeonato;
    private Arbitro arbitro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        campeonato = Campeonato.builder()
                .nome("Copa do Brasil")
                .build();

        arbitro = Arbitro.builder()
                .nome("Daronco")
                .build();

        federacao = Federacao.builder()
                .nome("CBF")
                .lista_arbitro(List.of(arbitro))
                .lista_campeonato(List.of(campeonato))
                .build();
    }

    private List<FederacaoDTO> executarListagemBasica() {
        when(federacaoRepository.findAll()).thenReturn(List.of(federacao));
        return federacaoService.listarFederacao();
    }

    private FederacaoDTO federacaoDTO() {
        List<FederacaoDTO> resultado = executarListagemBasica();
        assertEquals(1, resultado.size());
        return resultado.get(0);
    }

    @Test
    void deveAdicionarCampeonatoNaFederacao() {
        federacao.setLista_campeonato(new ArrayList<>());
        federacaoService.adcionarCampeonato(federacao, campeonato);
        assertTrue(federacao.getLista_campeonato().contains(campeonato));
    }

    @Test
    void deveAdicionarFederacaoNoCampeonato() {
        federacao.setLista_campeonato(new ArrayList<>());
        federacaoService.adcionarCampeonato(federacao, campeonato);
        assertEquals(federacao, campeonato.getFederacao());
    }

    @Test
    void deveListarFederacoesComONomeConvertidoParaDTO() {
        assertEquals("CBF", federacaoDTO().getNome());
    }

    @Test
    void deveListarFederacoesComOsCampeonatosConvertidosParaDTO() {
        assertEquals(1, federacaoDTO().getCampeonatos().size());
        assertTrue(federacaoDTO().getCampeonatos().contains("Copa do Brasil"));
    }

    @Test
    void deveListarFederacoesComOsArbitrosConvertidoParaDTO() {
        assertEquals(1, federacaoDTO().getArbitros().size());
        assertTrue(federacaoDTO().getArbitros().contains("Daronco"));
    }

    @Test
    void deveListarApenasNomesDasFederacoes() {
        when(federacaoRepository.findAll()).thenReturn(List.of(federacao));
        List<FederacaoNomeDTO> resultado = federacaoService.listarNome();
        assertEquals(1, resultado.size());
        assertEquals("CBF", resultado.get(0).getNome());
    }

    @Test
    void deveBuscarFederacaoPorNomeComSucesso() {
        when(federacaoRepository.findByNome("CBF")).thenReturn(Optional.of(federacao));

        FederacaoDTO dto = federacaoService.buscarFederacaoPorNome("CBF");

        assertEquals("CBF", dto.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoFederacaoNaoEncontrada() {
        when(federacaoRepository.findByNome("Inexistente")).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () ->
                federacaoService.buscarFederacaoPorNome("Inexistente"));

        assertEquals("Federação não encontrada: Inexistente", excecao.getMessage());
    }

    @Test
    void deveSalvarFederacaoNoRepositorio() {
        Federacao federacao = Federacao.builder()
                .nome("Barcelona")
                .build();

        when(federacaoRepository.save(any(Federacao.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // simula comportamento real do save()

        federacaoService.adicionarFederacao(federacao);

        ArgumentCaptor<Federacao> captor = ArgumentCaptor.forClass(Federacao.class);
        verify(federacaoRepository, times(1)).save(captor.capture());

        Federacao capturada = captor.getValue();

        assertEquals("Barcelona", capturada.getNome(), "O nome da equipe deve ser 'Barcelona'");
    }
}
