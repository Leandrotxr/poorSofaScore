package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.ArbitroService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ArbitroControllerTest {

    @Mock
    private ArbitroService arbitroService;

    @InjectMocks
    private ArbitroController arbitroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para GET /arbitros
    @Test
    void deveListarArbitros() {
        ArbitroDTO arbitroDTO = ArbitroDTO.builder().nome("Daronco").build();
        when(arbitroService.listarArbitros()).thenReturn(List.of(arbitroDTO));

        List<ArbitroDTO> resultado = arbitroController.listarArbitros();

        assertEquals(1, resultado.size());
        assertEquals("Daronco", resultado.get(0).getNome());
        verify(arbitroService, times(1)).listarArbitros();
    }

    // Teste para GET /arbitros/nomes
    @Test
    void deveListarNomesDosArbitros() {
        ArbitroNomeDTO arbitroNomeDTO = new ArbitroNomeDTO("Daronco");
        when(arbitroService.listarNome()).thenReturn(List.of(arbitroNomeDTO));

        List<ArbitroNomeDTO> resultado = arbitroController.listarNomes();

        assertEquals("Daronco", resultado.get(0).getNome());
        verify(arbitroService, times(1)).listarNome();
    }

    // Teste para GET /arbitros/{nome}
    @Test
    void deveBuscarArbitroPorNome() {
        ArbitroDTO arbitroDTO = ArbitroDTO.builder().nome("Daronco").build();
        when(arbitroService.buscarArbitroPorNome("Daronco")).thenReturn(arbitroDTO);

        ArbitroDTO resultado = arbitroController.buscarArbitroPorNome("Daronco");

        assertEquals("Daronco", resultado.getNome());
        verify(arbitroService, times(1)).buscarArbitroPorNome("Daronco");
    }

    // Teste para POST /arbitros/adicionarArbitro
    @Test
    void deveAdicionarArbitro() {
        Arbitro arbitro = new Arbitro();

        arbitroController.adicionarArbitro(arbitro);

        verify(arbitroService, times(1)).adicionarArbitro(arbitro);
    }
}
