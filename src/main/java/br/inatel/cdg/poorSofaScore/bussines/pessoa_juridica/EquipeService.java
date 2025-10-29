package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.PatrocinioDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.Patrocinio;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.EquipeRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.PatrocinadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final PatrocinadorRepository patrocinadorRepository;

    public EquipeService(EquipeRepository equipeRepository, PatrocinadorRepository patrocinadorRepository) {
        this.equipeRepository = equipeRepository;
        this.patrocinadorRepository = patrocinadorRepository;
    }

    @Transactional
    public void contratarPatrocinio(Equipe equipe, Patrocinador patrocinador, int valor) {

        Patrocinio patrocinio = Patrocinio.builder()
                .equipe(equipe)
                .patrocinador(patrocinador)
                .valor(valor)
                .build();

        equipe.getPatrocinios().add(patrocinio);
        equipeRepository.save(equipe);
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
                        .campeonatos(equipe.getLista_campeonatos().stream()
                                .map(Campeonato::getNome)
                                .collect(Collectors.toList()))
                        .patrocinios(equipe.getPatrocinios().stream()
                                .map(p -> PatrocinioDTO.builder()
                                        .patrocinador(p.getPatrocinador().getNome())
                                        .valor(p.getValor())
                                        .build())
                                .collect(Collectors.toList()))
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
