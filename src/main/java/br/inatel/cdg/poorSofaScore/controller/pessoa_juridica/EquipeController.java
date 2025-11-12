package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;


import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.EquipeService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipes")
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

    @PostMapping("/adicionarEquipe")
    public ResponseEntity<Equipe> adicionarEquipe(@Validated @RequestBody Equipe equipe) {
        Equipe novaEquipe = equipeService.adicionarEquipe(equipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEquipe);
    }
}
