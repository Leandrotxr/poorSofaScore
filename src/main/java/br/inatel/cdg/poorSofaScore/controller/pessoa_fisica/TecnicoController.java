package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.TecnicoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping
    public List<TecnicoDTO> listarTecnicos() {
        return tecnicoService.listarTecnicos();
    }

    @GetMapping("/nomes")
    public List<TecnicoNomeDTO> listarNomes() {
        return tecnicoService.listarNome();
    }

    @GetMapping("/{nome}")
    public TecnicoDTO buscarTecnicoPorNome(@PathVariable String nome) {
        return tecnicoService.buscarTecnicoPorNome(nome);
    }

    @PostMapping("/adicionarTecnico")
    public ResponseEntity<Tecnico> adicionarTecnico(@Validated @RequestBody Tecnico tecnico) {
        Tecnico novoTecnico = tecnicoService.adicionarTecnico(tecnico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTecnico);
    }
}
