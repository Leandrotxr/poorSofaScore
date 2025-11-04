package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FederacaoRepositoryTest {

    @Autowired
    private FederacaoRepository federacaoRepository;

    private Federacao fifa;

    @BeforeEach
    void setUp() {
        fifa = Federacao.builder()
                .nome("fifa")
                .cnpj("123")
                .build();
        federacaoRepository.save(fifa);
    }

    @Test
    void deveBuscarArbitroPorNome() {
        Optional<Federacao> encontrado = federacaoRepository.findByNome("fifa");
        assertTrue(encontrado.isPresent());
    }

    @Test
    void deveRetornarNomeCorretoAoBuscarPorNome() {
        Optional<Federacao> encontrado = federacaoRepository.findByNome("fifa");
        assertEquals("fifa", encontrado.get().getNome());
    }
}
