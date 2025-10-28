package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.PatrocinadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patrocinador")
public class PatrocinadorController {

    @Autowired
    private PatrocinadorService patrocinadorService;

    @GetMapping
    public List<Patrocinador> listarCampeonatos() {
        return patrocinadorService.listAll();
    }

    @GetMapping("/nomes")
    public List<String> listarNomes() {
        return patrocinadorService.listarNome();
    }
}
