package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.PatrocinadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.PatrocinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatrocinadorService {

    @Autowired
    private PatrocinadorRepository patrocinadorRepository;

    public List<PatrocinadorNomeDTO> listarNome() {
        return patrocinadorRepository.findAll()
                .stream()
                .map(patrocinador -> new PatrocinadorNomeDTO(patrocinador.getNome()))
                .collect(Collectors.toList());
    }

    public Patrocinador adicionarPatrocinador(Patrocinador patrocinador) {
        if(patrocinador.getNome() == null || patrocinador.getNome().isBlank())
            throw new IllegalArgumentException("Nome do patrocinador é obrigatório");
        if(patrocinador.getCnpj() == null || patrocinador.getCnpj().isBlank())
            throw new IllegalArgumentException("CNPJ do patrocinador é obrigatório");

        return patrocinadorRepository.save(patrocinador);
    }
}
