package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.interfaces.Contratavel;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "arbitro")
@Entity
public class Arbitro extends Pessoa implements Contratavel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Federacao federacao;

    public Arbitro(String nome, String cpf, int idade) {
        super(nome, cpf, idade);
    }

    @Override
    public void contratar(Federacao federacao) {
        this.federacao = federacao;
        federacao.getLista_arbitro().add(this);
    }
}
