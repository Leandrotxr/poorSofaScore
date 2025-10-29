package br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatrocinioDTO {
    private String patrocinador;
    private Integer valor;
}
