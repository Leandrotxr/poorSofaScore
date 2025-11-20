package br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemitirJogadorDTO {
    private String nomeEquipe;
    private String nomeJogador;
}
