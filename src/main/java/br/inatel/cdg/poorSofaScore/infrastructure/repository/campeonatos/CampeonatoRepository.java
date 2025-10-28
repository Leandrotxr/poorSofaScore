package br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {

    @Query("SELECT c.nome FROM Campeonato c")
    List<String> findAllNomes();
}
