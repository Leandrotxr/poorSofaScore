package br.inatel.cdg.poorSofaScore.bussines.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos.CampeonatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CampeonatoServiceTest {

    private CampeonatoService campeonatoService;
    private CampeonatoRepository campeonatoRepository;
    private Campeonato campeonato;
    private Federacao federacao;
    private Equipe equipe;

    @BeforeEach
    void setUp() {
        campeonatoRepository = mock(CampeonatoRepository.class);
        campeonatoService = new CampeonatoService(campeonatoRepository);

        federacao = Federacao.builder()
                .nome("FA")
                .build();

        equipe = Equipe.builder()
                .lista_campeonatos(new ArrayList<>())
                .nome("Arsenal")
                .build();

        campeonato = Campeonato.builder()
                .nome("Premier League")
                .local("Inglaterra")
                .premio(200000000)
                .federacao(federacao)
                .equipes(new ArrayList<>())
                .build();
    }

    private List<CampeonatoDTO> executarListagemBasica() {
        when(campeonatoRepository.findAll()).thenReturn(List.of(campeonato));
        return campeonatoService.listarCampeonatos();
    }

    private CampeonatoDTO getDTO() {
        List<CampeonatoDTO> lista = executarListagemBasica();
        assertEquals(1, lista.size());
        return lista.get(0);
    }

    @Test
    void deveSalvarNovoCampeonato() {

        Campeonato novoCampeonato = Campeonato.builder().nome("Copa Sul").local("BR").premio(100).build();

        campeonatoService.adicionarCampeonato(novoCampeonato);

        verify(campeonatoRepository).save(novoCampeonato);

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

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremCampeonatos() {
        when(campeonatoRepository.findAll()).thenReturn(List.of());

        List<CampeonatoDTO> resultado = campeonatoService.listarCampeonatos();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAdicionarCampeonatoNaEquipeEAEquipeNoCampeonato() {
        campeonatoService.adicionarCampeonato(equipe, campeonato);

        assertEquals(1, equipe.getLista_campeonatos().size());
        assertEquals(1, campeonato.getEquipes().size());
    }

    @Test
    void deveListarCampeonatosComONomeConvertidoParaDTO() {
        assertEquals("Premier League", getDTO().getNome());
    }

    @Test
    void deveListarCampeonatosComOLocalConvertidoParaDTO() {
        assertEquals("Inglaterra", getDTO().getLocal());
    }

    @Test
    void deveListarCampeonatosComOPremioConvertidoParaDTO() {
        assertEquals(200000000, getDTO().getPremio());
    }

    @Test
    void deveListarCampeonatosComAFederacaoConvertidaParaDTO() {
        assertEquals("FA", getDTO().getFederacao());
    }

    @Test
    void deveListarCampeonatosComEquipesConvertidasParaDTO() {
        campeonato.getEquipes().add(equipe);
        assertEquals(1, getDTO().getEquipes().size());
        assertTrue(getDTO().getEquipes().contains("Arsenal"));
    }

    @Test
    void deveListarApenasNomes() {
        when(campeonatoRepository.findAll()).thenReturn(List.of(campeonato));

        List<CampeonatoNomeDTO> lista = campeonatoService.listarNome();

        assertEquals("Premier League", lista.get(0).getNome());
    }


    @Test
    void deveRetornarDTOAoBuscarPorNome() {
        when(campeonatoRepository.findByNome("Premier League"))
                .thenReturn(Optional.of(campeonato));

        CampeonatoDTO dto = campeonatoService.buscarCampeonatoPorNome("Premier League");

        assertEquals("Premier League", dto.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoCampeonatoNaoEncontrado() {
        when(campeonatoRepository.findByNome("teste"))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> campeonatoService.buscarCampeonatoPorNome("teste"));

        assertTrue(ex.getMessage().contains("Campeonato não encontrada"));
    }

    @Test
    void deveSalvarCampeonatoValido() {
        when(campeonatoRepository.save(any(Campeonato.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        campeonatoService.adicionarCampeonato(campeonato);

        ArgumentCaptor<Campeonato> captor = ArgumentCaptor.forClass(Campeonato.class);
        verify(campeonatoRepository, times(1)).save(captor.capture());

        assertEquals("Premier League", captor.getValue().getNome());
    }
}
