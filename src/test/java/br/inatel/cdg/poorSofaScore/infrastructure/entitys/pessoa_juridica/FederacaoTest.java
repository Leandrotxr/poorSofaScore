package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.interfaces.Contratavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FederacaoTest {

    private Federacao federacao;

    @Mock
    private Contratavel contratavelMock;

    @BeforeEach
    void setUp() {
        federacao = new Federacao("Fifa","1000");
    }

    @Test
    void deveChamarContratarQuandoContratarEhChamado() {
        federacao.contratar(contratavelMock);

        verify(contratavelMock, times(1)).contratar(federacao);
        verifyNoMoreInteractions(contratavelMock);
    }
}
