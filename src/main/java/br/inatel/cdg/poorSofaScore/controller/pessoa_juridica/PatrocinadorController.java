package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.PatrocinadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patrocinador")
public class PatrocinadorController {

    @Autowired
    private PatrocinadorService patrocinadorService;

    @GetMapping("/nomes")
    public List<PatrocinadorNomeDTO> listarNomes() {
        return patrocinadorService.listarNome();
    }

    @PostMapping("/adicionarPatrocinador")
    public void adicionarPatrocinador(@RequestBody Patrocinador patrocinador) {
        patrocinadorService.adicionarPatrocinador(patrocinador);
    }
}
