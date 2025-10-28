package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.EquipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    public List<EquipeDTO> listarEquipes() {
        return equipeRepository.findAll().stream()
                .map(equipe -> EquipeDTO.builder()
                        .nome(equipe.getNome())
                        .fundacao(equipe.getFundacao())
                        .sede(equipe.getSede())
                        .jogadores(equipe.getLista_jogadores().stream()
                                .map(Jogador::getNome)
                                .collect(Collectors.toList()))
                        .tecnico(equipe.getTecnico() != null ? equipe.getTecnico().getNome() : null)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public Equipe salvarEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    public List<EquipeNomeDTO> listarNomes() {
        return equipeRepository.findAll()
                .stream()
                .map(equipe -> new EquipeNomeDTO(equipe.getNome()))
                .collect(Collectors.toList());
    }
}
