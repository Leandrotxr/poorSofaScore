package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ArbitroRepositoryTest {

    @Autowired
    private ArbitroRepository arbitroRepository;

    private Arbitro julio;

    @BeforeEach
    void setUp() {
        julio = Arbitro.builder()
                .nome("julio")
                .cpf("123")
                .idade(22)
                .build();
        arbitroRepository.save(julio);
    }

    @Test
    void deveBuscarArbitroPorNome() {
        Optional<Arbitro> encontrado = arbitroRepository.findByNome("julio");
        assertTrue(encontrado.isPresent());
    }

    @Test
    void deveRetornarNomeCorretoAoBuscarPorNome() {
        Optional<Arbitro> encontrado = arbitroRepository.findByNome("julio");
        assertEquals("julio", encontrado.get().getNome());
    }
}
