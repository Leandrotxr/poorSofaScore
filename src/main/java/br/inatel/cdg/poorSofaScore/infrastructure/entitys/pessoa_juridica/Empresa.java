package br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public abstract class Empresa {

    protected String nome;
    @JsonIgnore
    protected String cnpj;
}
