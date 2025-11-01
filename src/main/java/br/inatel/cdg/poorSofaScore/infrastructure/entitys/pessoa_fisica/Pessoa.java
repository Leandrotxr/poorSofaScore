package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class Pessoa {

    protected String nome;
    protected String cpf;
    @Setter
    protected int idade;

}
