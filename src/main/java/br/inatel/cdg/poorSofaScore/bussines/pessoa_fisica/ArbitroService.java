package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.ArbitroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArbitroService {

    @Autowired
    private ArbitroRepository arbitroRepository;

    public List<Arbitro> listAll() {
        return arbitroRepository.findAll();
    }

    public List<String> listarNome() {
        return arbitroRepository.findAllNomes();
    }
}
