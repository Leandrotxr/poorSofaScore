package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.PatrocinadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PatrocinadorControllerTest {

    @Mock
    private PatrocinadorService patrocinadorService;

    @InjectMocks
    private PatrocinadorController patrocinadorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para GET /patrocinador/nomes
    @Test
    void deveListarNomesDosPatrocinador() {
        PatrocinadorNomeDTO patrocinadorNomeDTO = new PatrocinadorNomeDTO("nike");
        when(patrocinadorService.listarNome()).thenReturn(List.of(patrocinadorNomeDTO));

        List<PatrocinadorNomeDTO> resultado = patrocinadorController.listarNomes();

        assertEquals("nike", resultado.get(0).getNome());
        verify(patrocinadorService, times(1)).listarNome();
    }

    // Teste para POST /patrocinador/adicionarPatrocinador
    @Test
    void deveAdicionarPatrocinador() {
        Patrocinador patrocinador = new Patrocinador();

        patrocinadorController.adicionarPatrocinador(patrocinador);

        verify(patrocinadorService, times(1)).adicionarPatrocinador(patrocinador);
    }
}
