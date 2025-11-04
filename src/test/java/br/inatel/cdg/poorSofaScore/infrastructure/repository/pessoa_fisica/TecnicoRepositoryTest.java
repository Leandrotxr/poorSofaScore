package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TecnicoRepositoryTest {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    private Tecnico diniz;

    @BeforeEach
    void setUp() {
        diniz = Tecnico.builder()
                .nome("diniz")
                .cpf("123")
                .idade(22)
                .build();
        tecnicoRepository.save(diniz);
    }

    @Test
    void deveBuscarArbitroPorNome() {
        Optional<Tecnico> encontrado = tecnicoRepository.findByNome("diniz");
        assertTrue(encontrado.isPresent());
    }

    @Test
    void deveRetornarNomeCorretoAoBuscarPorNome() {
        Optional<Tecnico> encontrado = tecnicoRepository.findByNome("diniz");
        assertEquals("diniz", encontrado.get().getNome());
    }
}
