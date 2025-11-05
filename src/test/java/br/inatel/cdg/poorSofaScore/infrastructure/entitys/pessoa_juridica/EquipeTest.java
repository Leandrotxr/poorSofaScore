package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.interfaces.Contratavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipeTest {

    private Equipe equipe;

    @Mock
    private Contratavel contratavelMock;

    @BeforeEach
    void setUp() {
        equipe = new Equipe("Flamengo", "12345678000199", 1895, "Rio de Janeiro");
    }

    @Test
    void deveChamarContratarQuandoContratarEhChamado() {
        equipe.contratar(contratavelMock);
        verify(contratavelMock, times(1)).contratar(equipe);
        verifyNoMoreInteractions(contratavelMock);
    }
}

