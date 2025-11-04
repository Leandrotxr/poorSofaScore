package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatrocinadorRepository extends JpaRepository<Patrocinador, Integer> {

    Optional<Patrocinador> findByNome(String nome);
}
