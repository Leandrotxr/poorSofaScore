package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.PatrocinadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatrocinadorServiceTest {

    @Mock
    private PatrocinadorRepository patrocinadorRepository;

    @InjectMocks
    private PatrocinadorService patrocinadorService;

    private Patrocinador patrocinador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        patrocinador = Patrocinador.builder()
                .nome("Red Bull")
                .cnpj("11.222.333/0001-00")
                .build();
    }

    private List<PatrocinadorNomeDTO> executarListagemBasica() {
        when(patrocinadorRepository.findAll()).thenReturn(List.of(patrocinador));
        return patrocinadorService.listarNome();
    }

    private PatrocinadorNomeDTO patrocinadorDTO() {
        List<PatrocinadorNomeDTO> resultado = executarListagemBasica();
        assertEquals(1, resultado.size());
        return resultado.get(0);
    }

    @Test
    void deveListarApenasNomesDosPatrocinadores() {
        assertEquals("Red Bull", patrocinadorDTO().getNome());
    }

    @Test
    void deveListarApenasUmPatrocinador() {
        assertEquals(1, executarListagemBasica().size());
    }

    @Test
    void deveAdicionarPatrocinadorComSucesso() {
        when(patrocinadorRepository.save(any(Patrocinador.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        patrocinadorService.adicionarPatrocinador(patrocinador);

        ArgumentCaptor<Patrocinador> captor = ArgumentCaptor.forClass(Patrocinador.class);
        verify(patrocinadorRepository, times(1)).save(captor.capture());

        Patrocinador capturado = captor.getValue();

        assertEquals("Red Bull", capturado.getNome());
        assertEquals("11.222.333/0001-00", capturado.getCnpj());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNuloOuVazio() {
        Patrocinador semNome = Patrocinador.builder()
                .cnpj("11.222.333/0001-00")
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> patrocinadorService.adicionarPatrocinador(semNome)
        );

        assertEquals("Nome do patrocinador é obrigatório", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCnpjForNuloOuVazio() {
        Patrocinador semCnpj = Patrocinador.builder()
                .nome("Red Bull")
                .build();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> patrocinadorService.adicionarPatrocinador(semCnpj)
        );

        assertEquals("CNPJ do patrocinador é obrigatório", ex.getMessage());
    }
}