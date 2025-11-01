package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TecnicoTest {

    private Tecnico tecnico;
    private Equipe equipeMock;

    @BeforeEach
    void setUp() {
        tecnico = Tecnico.builder()
                .nome("Pep Guardiola")
                .cpf("12345678900")
                .idade(53)
                .nacionalidade("Espanhola")
                .build();
        equipeMock = Mockito.mock(Equipe.class);
    }

    @Test
    void deveAtribuirEquipeQuandoContratarChamado() {
        tecnico.contratar(equipeMock);
        assertEquals(equipeMock, tecnico.getEquipe());
    }

    @Test
    void deveChamarSetTecnicoNaEquipeQuandoContratarChamado() {
        tecnico.contratar(equipeMock);
        verify(equipeMock).setTecnico(tecnico);
    }
}
