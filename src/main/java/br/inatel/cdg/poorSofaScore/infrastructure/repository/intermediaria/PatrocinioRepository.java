package br.inatel.cdg.poorSofaScore.infrastructure.repository.intermediaria;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.Patrocinio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatrocinioRepository extends JpaRepository<Patrocinio, Integer> {
}
