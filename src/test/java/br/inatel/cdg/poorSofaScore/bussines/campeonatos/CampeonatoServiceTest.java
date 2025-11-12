package br.inatel.cdg.poorSofaScore.bussines.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos.CampeonatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CampeonatoServiceTest {

    @InjectMocks
    private CampeonatoService campeonatoService;

    @Mock
    private CampeonatoRepository campeonatoRepository;
    private Campeonato campeonato;
    private Federacao federacaoMock;
    private List<Equipe> listaEquipeMock;
    private List<Campeonato> listaCampeonatoMock;

    @BeforeEach
    void setUp() {
        federacaoMock = Mockito.mock(Federacao.class);
        listaEquipeMock = Mockito.mock(List.class);
    }

    @Test
    void deveAdicionarFederacao() {
        campeonato.setFederacao(federacaoMock);
        assertEquals(federacaoMock, campeonato.getFederacao());
    }

    @Test
    void deveAdicionarNovoCampeonatoNaFederacao() {
        when(federacaoMock.getLista_campeonato()).thenReturn(listaCampeonatoMock);
        campeonato.setFederacao(federacaoMock);
        assertEquals(federacaoMock.getLista_campeonato(), listaCampeonatoMock);
    }

    @Test
    void deveAdicionarEquipe() {
        campeonato.setEquipes(listaEquipeMock);
        assertEquals(listaEquipeMock, campeonato.getEquipes());
    }

    @Test
    void deveSalvarNovoCampeonato() {

        Campeonato novoCampeonato = Campeonato.builder().nome("Copa Sul").local("BR").premio(100).build();

        campeonatoService.adicionarNovoCampeonato(novoCampeonato);

        verify(campeonatoRepository, times(1)).save(novoCampeonato);
    }


    @Test
    void deveRetornarCampeonatoDTO() {

        String nomeBusca = "Liga Teste";

        Campeonato entidade = Campeonato.builder().nome(nomeBusca).local("Paris").premio(500).equipes(List.of()).build();

        when(campeonatoRepository.findByNome(nomeBusca)).thenReturn(Optional.of(entidade));

        CampeonatoDTO resultado = campeonatoService.buscarCampeonatoPorNome(nomeBusca);

        verify(campeonatoRepository, times(1)).findByNome(nomeBusca);

        assertEquals(nomeBusca, resultado.getNome());
        assertEquals("Paris", resultado.getLocal());
    }

    @Test
    void deveLancarExcecao_QuandoNomeForNulo() {
        campeonato = Campeonato.builder()
                .local("Inglaterra")
                .premio(200000000)
                .build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                campeonatoService.adicionarCampeonato(campeonato));

        assertEquals("Nome do campeonato é obrigatório", ex.getMessage());

    }

    @Test
    void deveLancarExcecao_QuandoLocalForNulo() {
        campeonato = Campeonato.builder()
                .nome("Premier League")
                .premio(200000000)
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                campeonatoService.adicionarCampeonato(campeonato));

        assertEquals("Local do campeonato é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecao_QuandoPremioForZero() {
        campeonato = Campeonato.builder()
                .nome("Premier League")
                .local("Inglaterra")
                .build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                campeonatoService.adicionarCampeonato(campeonato));

        assertEquals("Prêmio do campeonato é obrigatório", ex.getMessage());
    }
}
