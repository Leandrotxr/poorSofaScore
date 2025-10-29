package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.Patrocinio;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.interfaces.Contratavel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patrocinador")
@Entity
public class Patrocinador extends Empresa implements Contratavel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idpatrocinador;
    @OneToMany(mappedBy = "patrocinador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patrocinio> listaEquipes = new ArrayList<>();

    public Patrocinador(String nome, String cnpj) {
        super(nome, cnpj);
    }

    @Override
    public void contratar(Equipe equipe, int valor) {
        Patrocinio patrocinio = Patrocinio.builder()
                .equipe(equipe)
                .patrocinador(this)
                .valor(valor) // pode vir de lógica futura
                .build();

        this.listaEquipes.add(patrocinio);
        equipe.getPatrocinios().add(patrocinio);
    }
}
