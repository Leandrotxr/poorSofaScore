package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.JogadorService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jogador")
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
}
