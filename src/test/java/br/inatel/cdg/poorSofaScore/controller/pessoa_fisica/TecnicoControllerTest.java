package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.TecnicoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class TecnicoControllerTest {

    @Mock
    private TecnicoService tecnicoService;

    @InjectMocks
    private TecnicoController tecnicoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para GET /tecnico
    @Test
    void deveListarTecnicos() {
        TecnicoDTO tecnicoDTO = TecnicoDTO.builder().nome("Guardiola").build();
        when(tecnicoService.listarTecnicos()).thenReturn(List.of(tecnicoDTO));

        List<TecnicoDTO> resultado = tecnicoController.listarTecnicos();

        assertEquals(1, resultado.size());
        assertEquals("Guardiola", resultado.get(0).getNome());
        verify(tecnicoService, times(1)).listarTecnicos();
    }

    // Teste para GET /tecnico/nomes
    @Test
    void deveListarNomesDosTecnicos() {
        TecnicoNomeDTO tecnicoNomeDTO = new TecnicoNomeDTO("Guardiola");
        when(tecnicoService.listarNome()).thenReturn(List.of(tecnicoNomeDTO));

        List<TecnicoNomeDTO> resultado = tecnicoController.listarNomes();

        assertEquals("Guardiola", resultado.get(0).getNome());
        verify(tecnicoService, times(1)).listarNome();
    }

    // Teste para GET /tecnico/{nome}
    @Test
    void deveBuscarTecnicoPorNome() {
        TecnicoDTO tecnicoDTO = TecnicoDTO.builder().nome("Guardiola").build();
        when(tecnicoService.buscarTecnicoPorNome("Guardiola")).thenReturn(tecnicoDTO);

        TecnicoDTO resultado = tecnicoController.buscarTecnicoPorNome("Guardiola");

        assertEquals("Guardiola", resultado.getNome());
        verify(tecnicoService, times(1)).buscarTecnicoPorNome("Guardiola");
    }

    // Teste para POST /tecnico/adicionarTecnico
    @Test
    void deveAdicionarTecnico() {
        Tecnico tecnico = new Tecnico();

        tecnicoController.adicionarTecnico(tecnico);

        verify(tecnicoService, times(1)).adicionarTecnico(tecnico);
    }
}
