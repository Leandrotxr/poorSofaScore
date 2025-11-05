package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.FederacaoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FederacaoServiceTest {

    @Mock
    private FederacaoRepository federacaoRepository;

    @InjectMocks
    private FederacaoService federacaoService;

    @Test
    void deveSalvarNovaFederacao() {

        Federacao novaFederacao = Federacao.builder().
                nome("FIFA teste").
                build();

        federacaoService.adicionarFederacao(novaFederacao);

        verify(federacaoRepository, times(1)).save(novaFederacao);
    }

    @Test
    void deveRetornarFederacaoDTO() {

        String nomeBusca = "CBF";

        Federacao entidade = Federacao.builder().
                nome(nomeBusca).
                build();
        entidade.setLista_arbitro(new ArrayList<>());
        entidade.setLista_campeonato(new ArrayList<>());

        when(federacaoRepository.findByNome(nomeBusca))
                .thenReturn(Optional.of(entidade));

        FederacaoDTO resultado = federacaoService.buscarFederacaoPorNome(nomeBusca);

        verify(federacaoRepository, times(1)).findByNome(nomeBusca);

        assertNotNull(resultado);
        assertEquals(nomeBusca, resultado.getNome());
    }

    @Test
    void deveLancarExcecao() {
        String nomeInexistente = "FIFA Inexistente";

        when(federacaoRepository.findByNome(nomeInexistente))
                .thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            federacaoService.buscarFederacaoPorNome(nomeInexistente);
        });

        assertTrue(excecao.getMessage().contains("Federação não encontrada: " + nomeInexistente));

        verify(federacaoRepository, times(1)).findByNome(nomeInexistente);
    }

}