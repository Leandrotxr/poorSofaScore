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
@Table (name = "tecnico")
@Entity
public class Tecnico extends Pessoa implements Contratavel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idtecnico;
    private String nacionalidade;
    @OneToOne
    private Equipe equipe;

    public Tecnico(String nome, String cpf, int idade, String nacionalidade) {
        super(nome, cpf, idade);
        this.nacionalidade = nacionalidade;
    }

    @Override
    public void contratar(Equipe equipe) {
        this.equipe = equipe;
        equipe.setTecnico(this);
    }
}
