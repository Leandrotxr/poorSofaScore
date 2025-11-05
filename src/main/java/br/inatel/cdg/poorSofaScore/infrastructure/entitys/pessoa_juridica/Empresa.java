package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass

public abstract class Empresa {

    protected String nome;
    protected String cnpj;
}
