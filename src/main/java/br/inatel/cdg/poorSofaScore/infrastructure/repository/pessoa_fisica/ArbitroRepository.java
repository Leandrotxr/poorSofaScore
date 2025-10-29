package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArbitroRepository extends JpaRepository<Arbitro, Integer> {

    @Query("SELECT a.nome FROM Arbitro a")
    List<String> findAllNomes();

    Optional<Arbitro> findByNome(String nome);
}
