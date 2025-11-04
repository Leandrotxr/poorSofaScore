package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FederacaoRepository extends JpaRepository<Federacao, Integer> {

    Optional<Federacao> findByNome(String nome);
}
