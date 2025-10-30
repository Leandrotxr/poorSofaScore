package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.FederacaoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoNomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/federacao")
public class FederacaoController {

    @Autowired
    private FederacaoService federacaoService;

    @GetMapping
    public List<FederacaoDTO> listarFederacoes() {
        return federacaoService.listarFederacao();
    }

    @GetMapping("/nomes")
    public List<FederacaoNomeDTO> listarNomes() {
        return federacaoService.listarNome();
    }

    @GetMapping("/{nome}")
    public FederacaoDTO buscarFederacaoPorNome(@PathVariable String nome) {
        return federacaoService.buscarFederacaoPorNome(nome);
    }
}
