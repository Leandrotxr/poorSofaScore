package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ArbitroRepository extends JpaRepository<Arbitro, Integer> {

    Optional<Arbitro> findByNome(String nome);
}
