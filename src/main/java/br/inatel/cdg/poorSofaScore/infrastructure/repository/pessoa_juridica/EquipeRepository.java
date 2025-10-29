package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    @Query("SELECT e.nome FROM Equipe e")
    List<String> findAllNomes();

    Optional<Equipe> findByNome(String nome);

}
