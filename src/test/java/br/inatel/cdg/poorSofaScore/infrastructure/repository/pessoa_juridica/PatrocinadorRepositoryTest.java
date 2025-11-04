package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PatrocinadorRepositoryTest {

    @Autowired
    private PatrocinadorRepository patrocinadorRepository;

    private Patrocinador puma;

    @BeforeEach
    void setUp() {
        puma = Patrocinador.builder()
                .nome("puma")
                .cnpj("123")
                .build();
        patrocinadorRepository.save(puma);
    }

    @Test
    void deveBuscarArbitroPorNome() {
        Optional<Patrocinador> encontrado = patrocinadorRepository.findByNome("puma");
        assertTrue(encontrado.isPresent());
    }

    @Test
    void deveRetornarNomeCorretoAoBuscarPorNome() {
        Optional<Patrocinador> encontrado = patrocinadorRepository.findByNome("puma");
        assertEquals("puma", encontrado.get().getNome());
    }
}
