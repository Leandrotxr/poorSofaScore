package br.inatel.cdg.poorSofaScore.controller.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.FederacaoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/federacoes")
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

    @PostMapping("/adicionarFederacao")
    public ResponseEntity<Federacao> adicionarFederacao(@RequestBody Federacao federacao) {
        Federacao novafederacao = federacaoService.adicionarFederacao(federacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novafederacao);
    }
}
