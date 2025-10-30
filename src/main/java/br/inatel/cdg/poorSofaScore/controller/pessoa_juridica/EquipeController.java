package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;


import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.EquipeService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @GetMapping
    public List<EquipeDTO> listarEquipes() {
        return equipeService.listarEquipes();
    }

    @GetMapping("/nomes")
    public List<EquipeNomeDTO> listarNomes() {
        return equipeService.listarNomes();
    }

    @GetMapping("/{nome}")
    public EquipeDTO buscarEquipePorNome(@PathVariable String nome) {
        return equipeService.buscarEquipePorNome(nome);
    }
}
