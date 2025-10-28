package br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampeonatoDTO {
    private String nome;
    private String local;
    private int premio;
    private String federacao;
    private List<String> equipes;
}
