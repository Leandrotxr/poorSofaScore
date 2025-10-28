package br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name = "patrocinio")
@Entity
public class Patrocinio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Equipe equipe;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patrocinador patrocinador;

    private Integer valor;
}
