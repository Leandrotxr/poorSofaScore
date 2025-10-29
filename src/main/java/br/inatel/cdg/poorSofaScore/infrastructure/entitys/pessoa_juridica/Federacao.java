package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.interfaces.Contratavel;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "federacao")
@Entity
public class Federacao extends Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToMany(mappedBy = "federacao")
    private List<Arbitro> lista_arbitro = new ArrayList<>();
    @OneToMany(mappedBy = "federacao")
    private List<Campeonato> lista_campeonato = new ArrayList<>();

    public Federacao(String nome, String cnpj) {
        super(nome, cnpj);
    }

    public void contratar(Contratavel contratavel) {
        contratavel.contratar(this);
    }
}
