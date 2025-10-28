package br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JogadorDTO {

    private String nome;
    private int idade;
    private String nacionalidade;
    private String posicao;
    private String equipe;
}
