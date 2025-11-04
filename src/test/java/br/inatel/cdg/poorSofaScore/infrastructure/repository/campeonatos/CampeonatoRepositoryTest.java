package br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CampeonatoRepositoryTest {

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    private Campeonato brasileirao;

    @BeforeEach
    void setUp() {
        brasileirao = Campeonato.builder()
                        .nome("Brasileirão")
                        .local("brasil")
                        .premio(1000)
                        .build();
        campeonatoRepository.save(brasileirao);
    }

    @Test
    void deveBuscarCampeonatoPorNome() {
        Optional<Campeonato> encontrado = campeonatoRepository.findByNome("Brasileirão");
        assertTrue(encontrado.isPresent());
    }

    @Test
    void deveRetornarNomeCorretoAoBuscarPorNome() {
        Optional<Campeonato> encontrado = campeonatoRepository.findByNome("Brasileirão");
        assertEquals("Brasileirão", encontrado.get().getNome());
    }
}

