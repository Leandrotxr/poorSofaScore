package br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {

    @Query("SELECT c.nome FROM Campeonato c")
    List<String> findAllNomes();

    Optional<Campeonato> findByNome(String nome);
}
