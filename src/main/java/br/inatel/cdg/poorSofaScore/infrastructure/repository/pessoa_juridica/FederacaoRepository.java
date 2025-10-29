package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FederacaoRepository extends JpaRepository<Federacao, Integer> {

    @Query("SELECT f.nome FROM Federacao f")
    List<String> findAllNomes();

    Optional<Federacao> findByNome(String nome);
}
