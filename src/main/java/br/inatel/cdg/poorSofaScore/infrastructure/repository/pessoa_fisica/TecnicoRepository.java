package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    @Query("SELECT t.nome FROM Tecnico t")
    List<String> findAllNomes();

    Optional<Tecnico> findByNome(String nome);
}
