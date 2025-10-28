package br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatrocinadorDTO {

    private String nome;
    private List<String> patrocinios;
}
