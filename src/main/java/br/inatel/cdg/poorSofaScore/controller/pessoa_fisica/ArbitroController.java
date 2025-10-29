package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.ArbitroService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroNomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/arbitro")
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
}
