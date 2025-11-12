package br.inatel.cdg.poorSofaScore.controller.campeonatos;

import br.inatel.cdg.poorSofaScore.bussines.campeonatos.CampeonatoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    @GetMapping
    public List<CampeonatoDTO> listarCampeonatos() {
        return campeonatoService.listarCampeonatos();
    }

    @GetMapping("/nomes")
    public List<CampeonatoNomeDTO> listarNomes() {
        return campeonatoService.listarNome();
    }

    @GetMapping("/{nome}")
    public CampeonatoDTO buscarCampeonatoPorNome(@PathVariable String nome) {
        return campeonatoService.buscarCampeonatoPorNome(nome);
    }

    @PostMapping("/adicionarCampeonato")
    public void adicionarCampeonato(@RequestBody Campeonato campeonato) {
        campeonatoService.adicionarCampeonato(campeonato);
    }
}
