package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.TecnicoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TecnicoServiceTest {

    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private TecnicoService tecnicoService;

    @Test
    void deveAdicionarTecnico() {
        Tecnico tecnico = Tecnico.builder()
                .nome("Carlo Ancelotti")
                .idade(64)
                .nacionalidade("Italiana")
                .build();

        tecnicoService.adicionarTecnico(tecnico);

        verify(tecnicoRepository, times(1)).save(tecnico);
    }

    @Test
    void deveListarTecnicosRetornandoTecnicoDTO() {

        Equipe equipe = Equipe.builder()
                .nome("Real Madrid")
                .build();

        Tecnico tecnico = Tecnico.builder()
                .nome("Ancelotti")
                .idade(64)
                .nacionalidade("Italiana")
                .equipe(equipe)
                .build();

        when(tecnicoRepository.findAll()).thenReturn(List.of(tecnico));

        List<TecnicoDTO> resultado = tecnicoService.listarTecnicos();

        verify(tecnicoRepository, times(1)).findAll();

        assertEquals(1, resultado.size());
        assertEquals("Ancelotti", resultado.get(0).getNome());
        assertEquals("Real Madrid", resultado.get(0).getEquipe());
    }

    @Test
    void deveListarSomenteNomesDosTecnicos() {

        Tecnico tecnico = Tecnico.builder()
                .nome("Dorival Jr")
                .idade(61)
                .nacionalidade("Brasileira")
                .build();

        when(tecnicoRepository.findAll()).thenReturn(List.of(tecnico));

        List<TecnicoNomeDTO> resultado = tecnicoService.listarNome();

        verify(tecnicoRepository, times(1)).findAll();
        assertEquals(1, resultado.size());
        assertEquals("Dorival Jr", resultado.get(0).getNome());
    }

    @Test
    void deveBuscarTecnicoPorNome() {

        Tecnico tecnico = Tecnico.builder()
                .nome("Tite")
                .idade(62)
                .nacionalidade("Brasileira")
                .build();

        when(tecnicoRepository.findByNome("Tite")).thenReturn(Optional.of(tecnico));

        TecnicoDTO resultado = tecnicoService.buscarTecnicoPorNome("Tite");

        verify(tecnicoRepository, times(1)).findByNome("Tite");

        assertNotNull(resultado);
        assertEquals("Tite", resultado.getNome());
        assertEquals(62, resultado.getIdade());
    }

    @Test
    void deveLancarExcecaoQuandoTecnicoNaoEncontrado() {

        String nomeInexistente = "Zidane";

        when(tecnicoRepository.findByNome(nomeInexistente)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> tecnicoService.buscarTecnicoPorNome(nomeInexistente)
        );

        verify(tecnicoRepository, times(1)).findByNome(nomeInexistente);

        assertTrue(excecao.getMessage().contains("Tecnico não encontrada: " + nomeInexistente));
    }
}
