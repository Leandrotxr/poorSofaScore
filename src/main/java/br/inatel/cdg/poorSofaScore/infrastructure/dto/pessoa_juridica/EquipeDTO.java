package br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipeDTO {

    private String nome;
    private int fundacao;
    private String sede;
    private List<String> jogadores;
    private String tecnico;
}
