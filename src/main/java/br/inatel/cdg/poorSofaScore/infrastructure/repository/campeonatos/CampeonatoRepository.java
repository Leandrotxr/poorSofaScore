package br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {

    Optional<Campeonato> findByNome(String nome);
}
