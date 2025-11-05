package br.inatel.cdg.poorSofaScore.bussines.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CampeonatoServiceTest {

    private Campeonato campeonato;
    private Federacao federacaoMock;
    private List<Equipe> listaEquipeMock;
    private List<Campeonato> listaCampeonatoMock;

    @BeforeEach
    void setUp() {
        campeonato = Campeonato.builder()
                .nome("Premier League")
                .local("Inglaterra")
                .premio(200000000)
                .build();
        federacaoMock = Mockito.mock(Federacao.class);
        listaEquipeMock = Mockito.mock(List.class);
        when(federacaoMock.getLista_campeonato()).thenReturn(listaCampeonatoMock);
    }

    @Test
    void deveAdicionarFederacao() {
        campeonato.setFederacao(federacaoMock);
        assertEquals(federacaoMock, campeonato.getFederacao());
    }

    @Test
    void deveAdicionarCampeonatoNaFederacao() {
        campeonato.setFederacao(federacaoMock);
        assertEquals(federacaoMock.getLista_campeonato(), listaCampeonatoMock);
    }

    @Test
    void deveAdicionarEquipe() {
        campeonato.setEquipes(listaEquipeMock);
        assertEquals(listaEquipeMock, campeonato.getEquipes());
    }
}
