package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.DemitirJogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.PatrocinioDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.Patrocinio;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.JogadorRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.TecnicoRepository;
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
    private TecnicoRepository tecnicoRepository;
    private JogadorRepository jogadorRepository;
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
        tecnicoRepository = mock(TecnicoRepository.class);
        jogadorRepository = mock(JogadorRepository.class);
        equipeService = new EquipeService(equipeRepository, patrocinadorRepository, tecnicoRepository, jogadorRepository);

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
        equipe.setPatrocinios(new ArrayList<>());

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
                .cnpj("1234")
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

    @Test
    void deveLancarExcecaoQuandoNomeForNuloOuVazio() {
        Equipe equipe = Equipe.builder()
                .cnpj("12345678000199")
                .fundacao(1899)
                .sede("Barcelona")
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.adicionarEquipe(equipe)
        );

        assertEquals("Nome da equipe é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCnpjForNuloOuVazio() {
        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .fundacao(1899)
                .sede("Barcelona")
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.adicionarEquipe(equipe)
        );

        assertEquals("CNPJ da equipe é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoFundacaoForInvalida() {
        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .cnpj("12345678000199")
                .fundacao(0)
                .sede("Barcelona")
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.adicionarEquipe(equipe)
        );

        assertEquals("Fundação da equipe é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoSedeForNulaOuVazia() {
        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .cnpj("12345678000199")
                .fundacao(1899)
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.adicionarEquipe(equipe)
        );

        assertEquals("Sede da equipe é obrigatório", ex.getMessage());
    }

    @Test
    void deveContratarTecnicoComSucesso() {
        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .build();

        Tecnico tecnico = Tecnico.builder()
                .nome("Xavi")
                .build();

        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(equipe));
        when(tecnicoRepository.findByNome("Xavi")).thenReturn(Optional.of(tecnico));

        equipeService.contratarTecnico("Barcelona", "Xavi");

        assertEquals(tecnico, equipe.getTecnico());
        assertEquals(equipe, tecnico.getEquipe());

        verify(equipeRepository).save(equipe);
    }

    @Test
    void deveLancarExcecaoSeEquipeNaoExistirAoContratarTecnico() {
        when(equipeRepository.findByNome("teste")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> equipeService.contratarTecnico("teste", "Xavi"));

        assertTrue(ex.getMessage().contains("Equipe não encontrada"));
    }

    @Test
    void deveLancarExcecaoSeTecnicoNaoExistirAoContratar() {
        Equipe equipe = Equipe.builder().nome("Barcelona").build();

        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(equipe));
        when(tecnicoRepository.findByNome("Xavi")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> equipeService.contratarTecnico("Barcelona", "Xavi"));

        assertTrue(ex.getMessage().contains("Técnico não encontrado"));
    }

    @Test
    void naoDeveContratarTecnicoSeEquipeJaTiverUm() {
        Tecnico tecnicoAtual = Tecnico.builder().nome("Xavi").build();

        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .tecnico(tecnicoAtual)
                .build();

        Tecnico novoTecnico = Tecnico.builder().nome("Jose Mourinho").build();

        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(equipe));
        when(tecnicoRepository.findByNome("Jose Mourinho")).thenReturn(Optional.of(novoTecnico));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> equipeService.contratarTecnico("Barcelona", "Jose Mourinho"));

        assertEquals("A equipe já possui um técnico!", ex.getMessage());
    }

    @Test
    void naoDeveContratarTecnicoQueJaTemEquipe() {
        Equipe Comtecnico = Equipe.builder().nome("Barcelona").build();

        Equipe Semtecnico = Equipe.builder().nome("PSG").build();

        Tecnico tecnico = Tecnico.builder()
                .nome("Xavi")
                .equipe(Comtecnico)
                .build();

        when(equipeRepository.findByNome("PSG")).thenReturn(Optional.of(Semtecnico));
        when(tecnicoRepository.findByNome("Xavi")).thenReturn(Optional.of(tecnico));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> equipeService.contratarTecnico("PSG", "Xavi"));

        assertEquals("O técnico já está associado a uma equipe!", ex.getMessage());
    }


    @Test
    void deveDemitirTecnicoComSucesso() {
        Tecnico tecnico = Tecnico.builder().nome("Xavi").build();

        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .tecnico(tecnico)
                .build();

        tecnico.setEquipe(equipe);

        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(equipe));
        when(equipeRepository.save(any(Equipe.class))).thenAnswer(inv -> inv.getArgument(0));

        EquipeNomeDTO dto = new EquipeNomeDTO(equipe.getNome());

        Equipe result = equipeService.demitirTecnico(dto);

        assertNull(result.getTecnico());
        assertNull(tecnico.getEquipe());

        verify(equipeRepository).save(equipe);
    }

    @Test
    void deveLancarExcecaoSeNomeDaEquipeForInvalidoAoDemitir() {
        EquipeNomeDTO dto = new EquipeNomeDTO("");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> equipeService.demitirTecnico(dto));

        assertEquals("Nome da equipe é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeEquipeNaoExistirAoDemitir() {
        EquipeNomeDTO dto = new EquipeNomeDTO("teste");

        when(equipeRepository.findByNome("teste")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> equipeService.demitirTecnico(dto));

        assertEquals("Equipe não encontrada", ex.getMessage());
    }

    @Test
    void deveContratarJogadorComSucesso() {
        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .lista_jogadores(new ArrayList<>())
                .build();

        Jogador jogador = Jogador.builder()
                .nome("Messi")
                .build();

        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(equipe));
        when(jogadorRepository.findByNome("Messi")).thenReturn(Optional.of(jogador));

        equipeService.contratarJogador("Barcelona", "Messi");

        assertEquals(equipe, jogador.getEquipe());
        assertTrue(equipe.getLista_jogadores().contains(jogador));

        verify(jogadorRepository).save(jogador);
    }

    @Test
    void deveLancarExcecaoNomesInvalidosContratarJogador() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.contratarJogador("", "")
        );

        assertEquals("Nome da equipe e do jogador são obrigatórios", ex.getMessage());
    }
    @Test
    void deveLancarExcecaoQuandoEquipeNaoExistirContratarJogador() {
        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.contratarJogador("Barcelona", "Messi")
        );

        assertEquals("Equipe não encontrada", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoJogadorNaoExistirAoContratar() {
        Equipe equipe = Equipe.builder().nome("Barcelona").build();

        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(equipe));
        when(jogadorRepository.findByNome("Messi")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.contratarJogador("Barcelona", "Messi")
        );

        assertEquals("Jogador não encontrado", ex.getMessage());
    }

    @Test
    void naoDeveContratarJogadorQueJaTemEquipe() {
        Equipe equipe = Equipe.builder().nome("PSG").build();
        Jogador jogador = Jogador.builder()
                .nome("Neymar")
                .equipe(equipe)
                .build();

        Equipe barcelona = Equipe.builder().nome("Barcelona").build();

        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(barcelona));
        when(jogadorRepository.findByNome("Neymar")).thenReturn(Optional.of(jogador));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.contratarJogador("Barcelona", "Neymar")
        );

        assertEquals("O jogador já pertence à equipe PSG", ex.getMessage());
    }


    @Test
    void deveDemitirJogadorComSucesso() {
        Equipe equipe = Equipe.builder()
                .nome("Barcelona")
                .lista_jogadores(new ArrayList<>())
                .build();

        Jogador jogador = Jogador.builder()
                .nome("Messi")
                .equipe(equipe)
                .build();

        equipe.getLista_jogadores().add(jogador);

        DemitirJogadorDTO dto = new DemitirJogadorDTO("Barcelona", "Messi");

        when(jogadorRepository.findByNome("Messi")).thenReturn(Optional.of(jogador));
        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(equipe));
        when(equipeRepository.save(any(Equipe.class))).thenAnswer(inv -> inv.getArgument(0));

        Equipe result = equipeService.demitirJogador(dto);

        assertNull(jogador.getEquipe());
        assertFalse(result.getLista_jogadores().contains(jogador));
    }

    @Test
    void deveLancarExcecaoQuandoNomeEquipeInvalidoDemitirJogador() {
        DemitirJogadorDTO dto = new DemitirJogadorDTO("", "Messi");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.demitirJogador(dto)
        );

        assertEquals("Nome da equipe é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeJogadorInvalidoDemitirJogador() {
        DemitirJogadorDTO dto = new DemitirJogadorDTO("Barcelona", "");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.demitirJogador(dto)
        );

        assertEquals("Nome do jogador é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoJogadorNaoExistirDemitir() {
        when(jogadorRepository.findByNome("Messi")).thenReturn(Optional.empty());

        DemitirJogadorDTO dto = new DemitirJogadorDTO("Barcelona", "Messi");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.demitirJogador(dto)
        );

        assertEquals("Jogador não encontrado", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEquipeNaoExistirAoDemitirJogador() {
        Jogador jogador = Jogador.builder().nome("Messi").build();
        when(jogadorRepository.findByNome("Messi")).thenReturn(Optional.of(jogador));
        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.empty());

        DemitirJogadorDTO dto = new DemitirJogadorDTO("Barcelona", "Messi");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.demitirJogador(dto)
        );

        assertEquals("Equipe não encontrada", ex.getMessage());
    }


    @Test
    void naoDeveDemitirJogadorDeOutraEquipe() {
        Equipe barcelona = Equipe.builder().nome("Barcelona").build();
        Equipe psg = Equipe.builder().nome("PSG").build();

        Jogador jogador = Jogador.builder()
                .nome("Neymar")
                .equipe(psg)
                .build();

        DemitirJogadorDTO dto = new DemitirJogadorDTO("Barcelona", "Neymar");

        when(jogadorRepository.findByNome("Neymar")).thenReturn(Optional.of(jogador));
        when(equipeRepository.findByNome("Barcelona")).thenReturn(Optional.of(barcelona));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> equipeService.demitirJogador(dto)
        );

        assertEquals("O jogador não pertence a essa equipe", ex.getMessage());
    }



}