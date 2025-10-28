package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.ArbitroService;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
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
    public List<Arbitro> listarCampeonatos() {
        return arbitroService.listAll();
    }

    @GetMapping("/nomes")
    public List<String> listarNomes() {
        return arbitroService.listarNome();
    }
}
