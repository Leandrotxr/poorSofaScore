package br.inatel.cdg.poorSofaScore.controller.campeonatos;

import br.inatel.cdg.poorSofaScore.bussines.campeonatos.CampeonatoService;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    @GetMapping
    public List<Campeonato> listarCampeonatos() {
        return campeonatoService.listAll();
    }

    @GetMapping("/nomes")
    public List<String> listarNomes() {
        return campeonatoService.listarNome();
    }
}
