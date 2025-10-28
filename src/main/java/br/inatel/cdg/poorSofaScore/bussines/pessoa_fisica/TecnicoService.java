package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public List<Tecnico> listAll() {
        return tecnicoRepository.findAll();
    }

    public List<String> listarNome() {
        return tecnicoRepository.findAllNomes();
    }
}
