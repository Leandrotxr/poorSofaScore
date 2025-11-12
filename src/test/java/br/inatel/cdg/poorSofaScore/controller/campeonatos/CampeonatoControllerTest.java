package br.inatel.cdg.poorSofaScore.controller.campeonatos;

import br.inatel.cdg.poorSofaScore.bussines.campeonatos.CampeonatoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CampeonatoControllerTest {

    @Mock
    private CampeonatoService campeonatoService;

    @InjectMocks
    private CampeonatoController campeonatoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para GET /campeonatos
    @Test
    void deveListarCampeonatos() {
        CampeonatoDTO campeonatoDTO = CampeonatoDTO.builder().nome("Libertadores").build();
        when(campeonatoService.listarCampeonatos()).thenReturn(List.of(campeonatoDTO));

        List<CampeonatoDTO> resultado = campeonatoController.listarCampeonatos();

        assertEquals(1, resultado.size());
        assertEquals("Libertadores", resultado.get(0).getNome());
        verify(campeonatoService, times(1)).listarCampeonatos();
    }

    // Teste para GET /campeonatos/nomes
    @Test
    void deveListarNomesDosCampeonato() {
        CampeonatoNomeDTO campeonatoNomeDTO = new CampeonatoNomeDTO("Libertadores");
        when(campeonatoService.listarNome()).thenReturn(List.of(campeonatoNomeDTO));

        List<CampeonatoNomeDTO> resultado = campeonatoController.listarNomes();

        assertEquals("Libertadores", resultado.get(0).getNome());
        verify(campeonatoService, times(1)).listarNome();
    }

    // Teste para GET /campeonatos/{nome}
    @Test
    void deveBuscarCampeonatoPorNome() {
        CampeonatoDTO campeonatoDTO = CampeonatoDTO.builder().nome("Libertadores").build();
        when(campeonatoService.buscarCampeonatoPorNome("Libertadores")).thenReturn(campeonatoDTO);

        CampeonatoDTO resultado = campeonatoController.buscarCampeonatoPorNome("Libertadores");

        assertEquals("Libertadores", resultado.getNome());
        verify(campeonatoService, times(1)).buscarCampeonatoPorNome("Libertadores");
    }

    // Teste para POST /campeonatos/adicionarCampeonato
    @Test
    void deveAdicionarCampeonato() {
        Campeonato campeonato = new Campeonato();

        campeonatoController.adicionarCampeonato(campeonato);

        verify(campeonatoService, times(1)).adicionarNovoCampeonato(campeonato);
    }
}
