package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.EquipeService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.ContratarTecnicoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EquipeControllerTest {

    @Mock
    private EquipeService equipeService;

    @InjectMocks
    private EquipeController equipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para GET /equipes
    @Test
    void deveListarEquipes() {
        EquipeDTO equipeDTO = EquipeDTO.builder().nome("cruzeiro").build();
        when(equipeService.listarEquipes()).thenReturn(List.of(equipeDTO));

        List<EquipeDTO> resultado = equipeController.listarEquipes();

        assertEquals(1, resultado.size());
        assertEquals("cruzeiro", resultado.get(0).getNome());
        verify(equipeService, times(1)).listarEquipes();
    }

    // Teste para GET /equipes/nomes
    @Test
    void deveListarNomesDasEquipes() {
        EquipeNomeDTO equipeDTO = new EquipeNomeDTO("Corinthians");
        when(equipeService.listarNomes()).thenReturn(List.of(equipeDTO));

        List<EquipeNomeDTO> resultado = equipeController.listarNomes();

        assertEquals("Corinthians", resultado.get(0).getNome());
        verify(equipeService, times(1)).listarNomes();
    }

    // Teste para GET /equipes/{nome}
    @Test
    void deveBuscarEquipePorNome() {
        EquipeDTO equipeDTO = EquipeDTO.builder().nome("cruzeiro").build();
        when(equipeService.buscarEquipePorNome("cruzeiro")).thenReturn(equipeDTO);

        EquipeDTO resultado = equipeController.buscarEquipePorNome("cruzeiro");

        assertEquals("cruzeiro", resultado.getNome());
        verify(equipeService, times(1)).buscarEquipePorNome("cruzeiro");
    }

    // Teste para POST /equipes/adicionarEquipe
    @Test
    void deveAdicionarEquipe() {
        Equipe equipe = new Equipe();

        equipeController.adicionarEquipe(equipe);

        verify(equipeService, times(1)).adicionarEquipe(equipe);
    }

    // Teste para PATCH /equipes/contratarTecnico
    @Test
    void deveContratarTecnico() {
        ContratarTecnicoDTO dto = new ContratarTecnicoDTO("Flamengo", "Tite");

        ResponseEntity<String> response = equipeController.contratarTecnico(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Técnico contratado pela equipe com sucesso!", response.getBody());

        verify(equipeService, times(1))
                .contratarTecnico("Flamengo", "Tite");
    }

    // Teste para PATCH /equipes/demitirTecnico
    @Test
    void deveDemitirTecnico() {
        EquipeNomeDTO dto = new EquipeNomeDTO("Flamengo");

        ResponseEntity<String> response = equipeController.demitirTecnico(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Técnico da equipe Flamengo demitido", response.getBody());

        verify(equipeService, times(1))
                .demitirTecnico(dto);
    }

}
