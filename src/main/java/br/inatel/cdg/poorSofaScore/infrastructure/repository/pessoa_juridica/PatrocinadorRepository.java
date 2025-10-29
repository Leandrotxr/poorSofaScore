package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatrocinadorRepository extends JpaRepository<Patrocinador, Integer> {

    @Query("SELECT p.nome FROM Patrocinador p")
    List<String> findAllNomes();

    Optional<Patrocinador> findByNome(String nome);
}
