package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.TecnicoRepository;
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

public class TecnicoServiceTest {

    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private TecnicoService tecnicoService;

    private Tecnico tecnico;
    private Equipe equipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        equipe = Equipe.builder()
                .nome("Real Madrid")
                .build();

        tecnico = Tecnico.builder()
                .nome("Ancelotti")
                .idade(60)
                .cpf("123")
                .nacionalidade("Itália")
                .equipe(equipe)
                .build();
    }

    private List<TecnicoDTO> executarListagemBasica() {
        when(tecnicoRepository.findAll()).thenReturn(List.of(tecnico));
        return tecnicoService.listarTecnicos();
    }

    private TecnicoDTO tecnicoDTO() {
        List<TecnicoDTO> resultado = executarListagemBasica();
        assertEquals(1, resultado.size());
        return resultado.get(0);
    }

    // ---------------------
    // TESTES DE LISTAGEM
    // ---------------------

    @Test
    void deveListarTecnicosComNomeConvertidoParaDTO() {
        assertEquals("Ancelotti", tecnicoDTO().getNome());
    }

    @Test
    void deveListarTecnicosComIdadeConvertidaParaDTO() {
        assertEquals(60, tecnicoDTO().getIdade());
    }

    @Test
    void deveListarTecnicosComNacionalidadeConvertidaParaDTO() {
        assertEquals("Itália", tecnicoDTO().getNacionalidade());
    }

    @Test
    void deveListarTecnicosComEquipeConvertidaParaDTO() {
        assertEquals("Real Madrid", tecnicoDTO().getEquipe());
    }

    @Test
    void deveListarSomenteNomesDosTecnicos() {
        when(tecnicoRepository.findAll()).thenReturn(List.of(tecnico));

        List<TecnicoNomeDTO> nomes = tecnicoService.listarNome();

        assertEquals(1, nomes.size());
        assertEquals("Ancelotti", nomes.get(0).getNome());
    }

    // ---------------------
    // TESTES DE BUSCA POR NOME
    // ---------------------

    @Test
    void deveBuscarTecnicoPorNomeComSucesso() {
        when(tecnicoRepository.findByNome("Ancelotti")).thenReturn(Optional.of(tecnico));

        TecnicoDTO dto = tecnicoService.buscarTecnicoPorNome("Ancelotti");

        assertEquals("Ancelotti", dto.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoTecnicoNaoEncontrado() {
        when(tecnicoRepository.findByNome("Inexistente")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> tecnicoService.buscarTecnicoPorNome("Inexistente"));

        assertEquals("Tecnico não encontrada: Inexistente", ex.getMessage());
    }

    // ---------------------
    // TESTES DE ADIÇÃO
    // ---------------------

    @Test
    void deveSalvarTecnicoNoRepositorio() {

        when(tecnicoRepository.save(any(Tecnico.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        tecnicoService.adicionarTecnico(tecnico);

        ArgumentCaptor<Tecnico> captor = ArgumentCaptor.forClass(Tecnico.class);
        verify(tecnicoRepository).save(captor.capture());

        assertEquals("Ancelotti", captor.getValue().getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForInvalido() {
        Tecnico t = Tecnico.builder()
                .cpf("123")
                .idade(50)
                .nacionalidade("Brasil")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> tecnicoService.adicionarTecnico(t));

        assertEquals("Nome do tecnico é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        Tecnico t = Tecnico.builder()
                .nome("Teste")
                .idade(50)
                .nacionalidade("Brasil")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> tecnicoService.adicionarTecnico(t));

        assertEquals("CPF do tecnico é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoIdadeForInvalida() {
        Tecnico t = Tecnico.builder()
                .nome("Teste")
                .cpf("123")
                .idade(0)
                .nacionalidade("Brasil")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> tecnicoService.adicionarTecnico(t));

        assertEquals("Idade do tecnico é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNacionalidadeForInvalida() {
        Tecnico t = Tecnico.builder()
                .nome("Teste")
                .cpf("123")
                .idade(40)
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> tecnicoService.adicionarTecnico(t));

        assertEquals("Nacionalidade do tecnico é obrigatório", ex.getMessage());
    }
}
