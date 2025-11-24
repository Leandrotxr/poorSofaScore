package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.PatrocinadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patrocinadores")
public class PatrocinadorController {

    @Autowired
    private PatrocinadorService patrocinadorService;

    @GetMapping("/nomes")
    public List<PatrocinadorNomeDTO> listarNomes() {
        return patrocinadorService.listarNome();
    }

    @PostMapping("/adicionarPatrocinador")
    public ResponseEntity<Patrocinador> adicionarPatrocinador(@Validated @RequestBody Patrocinador patrocinador) {
        Patrocinador novoPatrocinador = patrocinadorService.adicionarPatrocinador(patrocinador);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPatrocinador);
    }
}
