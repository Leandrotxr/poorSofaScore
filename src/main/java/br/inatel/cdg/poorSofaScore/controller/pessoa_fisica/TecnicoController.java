package br.inatel.cdg.poorSofaScore.controller.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica.TecnicoService;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoNomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tecnico")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping
    public List<TecnicoDTO> listarTecnicos() {
        return tecnicoService.listarTecnicos();
    }

    @GetMapping("/nomes")
    public List<TecnicoNomeDTO> listarNomes() {
        return tecnicoService.listarNome();
    }
}
