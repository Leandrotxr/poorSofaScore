package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public abstract class Pessoa {

    protected String nome;
    @JsonIgnore
    protected String cpf;
    @Setter
    protected int idade;

}
