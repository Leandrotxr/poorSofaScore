package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.PatrocinadorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatrocinadorServiceTest {

    @Mock
    private PatrocinadorRepository patrocinadorRepository;

    @InjectMocks
    private PatrocinadorService patrocinadorService;

    @Test
    void deveSalvarNovoPatrocinador() {

        Patrocinador novoPatrocinador = new Patrocinador();
        //novoPatrocinador.setNome("Patrocinador Alpha");

        patrocinadorService.adicionarPatrocinador(novoPatrocinador);

        verify(patrocinadorRepository, times(1)).save(novoPatrocinador);
    }

    /*
    @Test
    void deveRetornarListaDePatrocinadores() {

        Patrocinador p1 = new Patrocinador();
        //p1.setNome("MegaCorp");

        Patrocinador p2 = new Patrocinador();
        //p2.setNome("GlobalTech");

        List<Patrocinador> entidades = List.of(p1, p2);

        when(patrocinadorRepository.findAll()).thenReturn(entidades);

        List<PatrocinadorNomeDTO> resultado = patrocinadorService.listarNome();
        verify(patrocinadorRepository, times(1)).findAll();

        assertEquals("MegaCorp", resultado.get(0).getNome());
        assertEquals("GlobalTech", resultado.get(1).getNome());

    }
    */
}