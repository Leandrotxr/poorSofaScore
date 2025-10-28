package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.FederacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FederacaoService {

    @Autowired
    private FederacaoRepository federacaoRepository;

    public List<Federacao> listAll() {
        return federacaoRepository.findAll();
    }

    public List<String> listarNome() {
        return federacaoRepository.findAllNomes();
    }
}
