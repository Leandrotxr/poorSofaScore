package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.Patrocinio;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.interfaces.Contratavel;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "equipe")
@Entity
public class Equipe extends Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idequipe;
    private int fundacao;
    private String sede;
    @OneToMany(mappedBy = "equipe")
    private List<Jogador> lista_jogadores = new ArrayList<>();
    @OneToOne
    private Tecnico tecnico;
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patrocinio> patrocinios = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Campeonato> lista_campeonatos = new ArrayList<>();

    public Equipe(String nome, String cnpj, int fundacao, String sede) {
        super(nome, cnpj);
        this.fundacao = fundacao;
        this.sede = sede;
    }

    public void contratar(Contratavel contratavel) {
        contratavel.contratar(this);
    }

}
