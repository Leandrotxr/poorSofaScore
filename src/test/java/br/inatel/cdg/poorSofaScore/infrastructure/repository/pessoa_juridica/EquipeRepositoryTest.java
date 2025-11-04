package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EquipeRepositoryTest {

    @Autowired
    private EquipeRepository equipeRepository;

    private Equipe cruzeiro;

    @BeforeEach
    void setUp() {
        cruzeiro = Equipe.builder()
                .nome("cruzeiro")
                .cnpj("123")
                .fundacao(1921)
                .sede("BH")
                .build();
        equipeRepository.save(cruzeiro);
    }

    @Test
    void deveBuscarArbitroPorNome() {
        Optional<Equipe> encontrado = equipeRepository.findByNome("cruzeiro");
        assertTrue(encontrado.isPresent());
    }

    @Test
    void deveRetornarNomeCorretoAoBuscarPorNome() {
        Optional<Equipe> encontrado = equipeRepository.findByNome("cruzeiro");
        assertEquals("cruzeiro", encontrado.get().getNome());
    }
}
