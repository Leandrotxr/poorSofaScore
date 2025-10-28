package br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TecnicoDTO {

    private String nome;
    private int idade;
    private String nacionalidade;
    private String equipe;
}
