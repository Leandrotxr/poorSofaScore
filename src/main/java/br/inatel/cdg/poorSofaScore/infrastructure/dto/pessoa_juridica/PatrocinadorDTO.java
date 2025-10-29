package br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.PatrocinioDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatrocinadorDTO {

    private String nome;
    private List<PatrocinioDTO> patrocinios;
}
