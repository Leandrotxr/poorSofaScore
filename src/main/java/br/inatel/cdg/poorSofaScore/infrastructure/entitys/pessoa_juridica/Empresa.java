package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public abstract class Empresa {

    protected String nome;
    protected String cnpj;
}
