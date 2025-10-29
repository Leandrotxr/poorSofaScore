package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.interfaces.Contratavel;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jogador")
@Entity
public class Jogador extends Pessoa implements Contratavel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nacionalidade;
    private String posicao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Equipe equipe;

    public Jogador(String nome, String cpf, int idade, String nacionalidade, String posicao) {
        super(nome, cpf, idade);
        this.nacionalidade = nacionalidade;
        this.posicao = posicao;
    }


    @Override
    public void contratar(Equipe equipe) {
        this.equipe = equipe;
        equipe.getLista_jogadores().add(this);
    }

}
