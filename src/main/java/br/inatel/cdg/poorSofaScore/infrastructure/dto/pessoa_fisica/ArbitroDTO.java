package br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArbitroDTO {

    private String nome;
    private int idade;
    private String federacao;
}
