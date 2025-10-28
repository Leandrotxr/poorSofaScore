package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.FederacaoService;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/federacao")
public class FederacaoController {

    @Autowired
    private FederacaoService federacaoService;

    @GetMapping
    public List<Federacao> listarCampeonatos() {
        return federacaoService.listAll();
    }

    @GetMapping("/nomes")
    public List<String> listarNomes() {
        return federacaoService.listarNome();
    }
}
