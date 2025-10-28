package br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "campeonato")
@Entity
public class Campeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String local;
    private int premio;
    @ManyToOne
    private Federacao federacao;
    @ManyToMany
    private List<Equipe> equipes = new ArrayList<>();

    public Campeonato(String nome, String local, int premio) {
        this.nome = nome;
        this.local = local;
        this.premio = premio;
    }
}
