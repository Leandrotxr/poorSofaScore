package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JogadorRepositoryTest {

    @Autowired
    private JogadorRepository jogadorRepository;

    private Jogador messi;

    @BeforeEach
    void setUp() {
        messi = Jogador.builder()
                .nome("messi")
                .cpf("123")
                .idade(22)
                .posicao("atacante")
                .nacionalidade("argentina")
                .build();
        jogadorRepository.save(messi);
    }

    @Test
    void deveBuscarArbitroPorNome() {
        Optional<Jogador> encontrado = jogadorRepository.findByNome("messi");
        assertTrue(encontrado.isPresent());
    }

    @Test
    void deveRetornarNomeCorretoAoBuscarPorNome() {
        Optional<Jogador> encontrado = jogadorRepository.findByNome("messi");
        assertEquals("messi", encontrado.get().getNome());
    }
}
