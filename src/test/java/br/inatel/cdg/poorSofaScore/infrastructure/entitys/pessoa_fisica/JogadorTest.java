package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class JogadorTest {

    private Jogador jogador;
    private Equipe equipeMock;
    private List<Jogador> listaMock;

    @BeforeEach
    void setUp() {
        jogador = Jogador.builder()
                .nome("Kaio Jorge")
                .cpf("12345678900")
                .idade(27)
                .nacionalidade("Brasil")
                .posicao("Atacante")
                .build();

        equipeMock = Mockito.mock(Equipe.class);

        listaMock = mock(List.class);
        when(equipeMock.getLista_jogadores()).thenReturn(listaMock);
    }

    @Test
    void deveAtribuirEquipeQuandoContratarChamado() {
        jogador.contratar(equipeMock);
        assertEquals(equipeMock, jogador.getEquipe());
    }

    @Test
    void deveAdicionarJogadorQuandoContratarChamado() {
        jogador.contratar(equipeMock);
        verify(listaMock).add(jogador);
    }
}
