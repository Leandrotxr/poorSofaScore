package br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.PatrocinioDTO;
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
    private List<String> campeonatos;
    private List<PatrocinioDTO> patrocinios;
}
