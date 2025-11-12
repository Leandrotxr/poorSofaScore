package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.ArbitroService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arbitros")
public class ArbitroController {

    @Autowired
    private ArbitroService arbitroService;

    @GetMapping
    public List<ArbitroDTO> listarArbitros() {
        return arbitroService.listarArbitros();
    }

    @GetMapping("/nomes")
    public List<ArbitroNomeDTO> listarNomes() {
        return arbitroService.listarNome();
    }

    @GetMapping("/{nome}")
    public ArbitroDTO buscarArbitroPorNome(@PathVariable String nome) {
        return arbitroService.buscarArbitroPorNome(nome);
    }

    @PostMapping("/adicionarArbitro")
    public ResponseEntity<Arbitro> adicionarArbitro(@Validated @RequestBody Arbitro arbitro) {
        Arbitro novoArbitro = arbitroService.adicionarArbitro(arbitro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoArbitro);
    }
}
