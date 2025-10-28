package br.inatel.cdg.poorSofaScore.bussines.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos.CampeonatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampeonatoService {

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    public List<Campeonato> listAll() {
        return campeonatoRepository.findAll();
    }

    public List<String> listarNome() {
        return campeonatoRepository.findAllNomes();
    }
}
