package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.JogadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    @GetMapping
    public List<JogadorDTO> listarJogadores() {
        return jogadorService.listarJogadores();
    }

    @GetMapping("/nomes")
    public List<JogadorNomeDTO> listarNomes() {
        return jogadorService.listarNome();
    }

    @GetMapping("/{nome}")
    public JogadorDTO buscarJogadorPorNome(@PathVariable String nome) {
        return jogadorService.buscarJogadorPorNome(nome);
    }

    @PostMapping("/adicionarJogador")
    public ResponseEntity<Jogador> adicionarJogador(@Validated @RequestBody Jogador jogador) {
        Jogador novoJogador = jogadorService.adicionarJogador(jogador);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoJogador);
    }
}
