package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.FederacaoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FederacaoControllerTest {

    @Mock
    private FederacaoService federacaoService;

    @InjectMocks
    private FederacaoController federacaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para GET /federacao
    @Test
    void deveListarFederacoes() {
        FederacaoDTO federacaoDTO = FederacaoDTO.builder().nome("fifa").build();
        when(federacaoService.listarFederacao()).thenReturn(List.of(federacaoDTO));

        List<FederacaoDTO> resultado = federacaoController.listarFederacoes();

        assertEquals(1, resultado.size());
        assertEquals("fifa", resultado.get(0).getNome());
        verify(federacaoService, times(1)).listarFederacao();
    }

    // Teste para GET /federacao/nomes
    @Test
    void deveListarNomesDasFederacoes() {
        FederacaoNomeDTO federacaoNomeDTO = new FederacaoNomeDTO("fifa");
        when(federacaoService.listarNome()).thenReturn(List.of(federacaoNomeDTO));

        List<FederacaoNomeDTO> resultado = federacaoController.listarNomes();

        assertEquals("fifa", resultado.get(0).getNome());
        verify(federacaoService, times(1)).listarNome();
    }

    // Teste para GET /federacao/{nome}
    @Test
    void deveBuscarFederacaoPorNome() {
        FederacaoDTO federacaoDTO = FederacaoDTO.builder().nome("fifa").build();
        when(federacaoService.buscarFederacaoPorNome("fifa")).thenReturn(federacaoDTO);

        FederacaoDTO resultado = federacaoController.buscarFederacaoPorNome("fifa");

        assertEquals("fifa", resultado.getNome());
        verify(federacaoService, times(1)).buscarFederacaoPorNome("fifa");
    }

    // Teste para POST /federacao/adicionarFederacao
    @Test
    void deveAdicionarFederacao() {
        Federacao federacao = new Federacao();

        federacaoController.adicionarFederacao(federacao);

        verify(federacaoService, times(1)).adicionarFederacao(federacao);
    }
}
