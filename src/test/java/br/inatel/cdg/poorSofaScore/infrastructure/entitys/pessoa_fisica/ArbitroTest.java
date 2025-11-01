package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ArbitroTest {

    private Arbitro arbitro;
    private Federacao federacaoMock;
    private List<Arbitro> listaMock;

    @BeforeEach
    void setUp() {
        arbitro = Arbitro.builder()
                .nome("Anderson Daronco")
                .cpf("12345678900")
                .idade(27)
                .build();
        federacaoMock = Mockito.mock(Federacao.class);
        listaMock = mock(List.class);
        when(federacaoMock.getLista_arbitro()).thenReturn(listaMock);
    }

    @Test
    void deveAtribuirEquipeQuandoContratarChamado() {
        arbitro.contratar(federacaoMock);
        assertEquals(federacaoMock, arbitro.getFederacao());
    }

    @Test
    void deveAdicionarJogadorQuandoContratarChamado() {
        arbitro.contratar(federacaoMock);
        verify(listaMock).add(arbitro);
    }
}
