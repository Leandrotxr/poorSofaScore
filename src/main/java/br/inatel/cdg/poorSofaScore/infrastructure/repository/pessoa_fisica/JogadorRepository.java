package br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    @Query("SELECT j.nome FROM Jogador j")
    List<String> findAllNomes();

    Optional<Jogador> findByNome(String nome);
}
