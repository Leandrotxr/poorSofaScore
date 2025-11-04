package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    Optional<Equipe> findByNome(String nome);

}
