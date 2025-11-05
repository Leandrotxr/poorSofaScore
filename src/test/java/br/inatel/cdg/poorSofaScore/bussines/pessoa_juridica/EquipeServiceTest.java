package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.PatrocinioDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.Patrocinio;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.EquipeRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.PatrocinadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquipeServiceTest {

    private EquipeRepository equipeRepository;
    private PatrocinadorRepository patrocinadorRepository;
    private EquipeService equipeService;
    private Tecnico tecnico;
    private Jogador jogador;
    private Campeonato campeonato;
    private Patrocinador patrocinador;
    private Equipe equipe;
    private Patrocinio patrocinio;

    @BeforeEach
    void setUp() {
        equipeRepository = mock(EquipeRepository.class);
        patrocinadorRepository = mock(PatrocinadorRepository.class);
        equipeService = new EquipeService(equipeRepository, patrocinadorRepository);

         tecnico = Tecnico.builder()
                .nome("Pep Guardiola")
                .build();

        jogador = Jogador.builder()
                .nome("Haaland")
                .build();

        campeonato = Campeonato.builder()
                .nome("Champions League")
                .build();

        patrocinador = Patrocinador.builder()
                .nome("Etihad Airways")
                .build();

        patrocinio = Patrocinio.builder()
                .patrocinador(patrocinador)
                .valor(1000000)
                .build();

        equipe = Equipe.builder()
                .nome("Manchester City")
                .fundacao(1880)
                .sede("Manchester")
                .tecnico(tecnico)
                .lista_jogadores(List.of(jogador))
                .lista_campeonatos(List.of(campeonato))
                .patrocinios(List.of(patrocinio))
                .build();
    }

    private List<EquipeDTO> executarListagemBasica() {
        when(equipeRepository.findAll()).thenReturn(List.of(equipe));
        return equipeService.listarEquipes();
    }

    private EquipeDTO getEquipeDTO() {
        List<EquipeDTO> resultado = executarListagemBasica();
        assertEquals(1, resultado.size());
        return resultado.get(0);
    }

    @Test
    void deveAdicionarPatrocinioEAoSalvarEquipe() {
        //Equipe equipe = new Equipe();
        equipe.setPatrocinios(new ArrayList<>()); //garante que lista de patrocinios está vazia

        Patrocinador patrocinador = new Patrocinador();

        equipeService.contratarPatrocinio(equipe, patrocinador, 1000);

        //usamos o captor para garantir que equipe está correta após modificar ela (adicionando patrocinador)
        ArgumentCaptor<Equipe> captor = ArgumentCaptor.forClass(Equipe.class);
        verify(equipeRepository, times(1)).save(captor.capture());

        assertEquals(1, captor.getValue().getPatrocinios().size());
    }

    @Test
    void deveListarEquipesComONomeConvertidoParaDTO() {
        assertEquals("Manchester City", getEquipeDTO().getNome());
    }

    @Test
    void deveListarEquipesComAFundacaoConvertidaParaDTO() {
        assertEquals(1880, getEquipeDTO().getFundacao());
    }

    @Test
    void deveListarEquipesComASedeConvertidaParaDTO() {
        assertEquals("Manchester", getEquipeDTO().getSede());
    }

    @Test
    void deveListarEquipesComOTecnicoConvertidoParaDTO() {
        assertEquals("Pep Guardiola", getEquipeDTO().getTecnico());
    }

    @Test
    void deveListarEquipesComOsJogadoresConvertidoParaDTO() {
        assertEquals(1, getEquipeDTO().getJogadores().size());
        assertTrue(getEquipeDTO().getJogadores().contains("Haaland"));
    }

    @Test
    void deveListarEquipesComOsCampeonatosConvertidoParaDTO() {
        assertEquals(1, getEquipeDTO().getCampeonatos().size());
        assertTrue(getEquipeDTO().getCampeonatos().contains("Champions League"));
    }

    @Test
    void deveListarEquipesComOsPatrociniosConvertidoParaDTO() {
        assertEquals(1, getEquipeDTO().getPatrocinios().size());
        PatrocinioDTO patDTO = getEquipeDTO().getPatrocinios().get(0);
        assertEquals("Etihad Airways", patDTO.getPatrocinador());
        assertEquals(1000000, patDTO.getValor());
    }

    @Test
    void deveListarApenasNomesDasEquipes() {
        when(equipeRepository.findAll()).thenReturn(List.of(equipe));
        List<EquipeNomeDTO> nomes = equipeService.listarNomes();
        assertEquals("Manchester City", nomes.get(0).getNome());
    }

    @Test
    void deveRetornarEquipeDTOQuandoEncontrarPorNome() {
        when(equipeRepository.findByNome("Manchester City")).thenReturn(Optional.of(equipe));

        EquipeDTO dto = equipeService.buscarEquipePorNome("Manchester City");

        assertEquals("Manchester City", dto.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoEquipeNaoEncontrada() {
        when(equipeRepository.findByNome("Inexistente")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> equipeService.buscarEquipePorNome("Inexistente"));

        assertTrue(ex.getMessage().contains("Equipe não encontrada"));
    }

    @Test
    void deveSalvarEquipeNoRepositorio() {
        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .fundacao(1899)
                .sede("Barcelona")
                .build();

        when(equipeRepository.save(any(Equipe.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // simula comportamento real do save()

        equipeService.adicionarEquipe(equipe);

        ArgumentCaptor<Equipe> captor = ArgumentCaptor.forClass(Equipe.class);
        verify(equipeRepository, times(1)).save(captor.capture());

        Equipe capturada = captor.getValue();

        assertEquals("Barcelona", capturada.getNome(), "O nome da equipe deve ser 'Barcelona'");
    }
}