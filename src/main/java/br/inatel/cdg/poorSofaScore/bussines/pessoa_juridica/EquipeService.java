package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.DemitirJogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.intermediaria.PatrocinioDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.EquipeNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.Patrocinio;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.JogadorRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.TecnicoRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.EquipeRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.PatrocinadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final PatrocinadorRepository patrocinadorRepository;
    private final TecnicoRepository tecnicoRepository;
    private final JogadorRepository jogadorRepository;

    public EquipeService(EquipeRepository equipeRepository, PatrocinadorRepository patrocinadorRepository, TecnicoRepository tecnicoRepository, JogadorRepository jogadorRepositor) {
        this.equipeRepository = equipeRepository;
        this.patrocinadorRepository = patrocinadorRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.jogadorRepository = jogadorRepositor;
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

    public List<EquipeNomeDTO> listarNomes() {
        return equipeRepository.findAll()
                .stream()
                .map(equipe -> new EquipeNomeDTO(equipe.getNome()))
                .collect(Collectors.toList());
    }

    public EquipeDTO buscarEquipePorNome(String nome) {
        Equipe equipe = equipeRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada: " + nome));

        return EquipeDTO.builder()
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
                .build();
    }

    public Equipe adicionarEquipe(Equipe equipe) {
        if(equipe.getNome() == null || equipe.getNome().isBlank())
            throw new IllegalArgumentException("Nome da equipe é obrigatório");
        if(equipe.getCnpj() == null || equipe.getCnpj().isBlank())
            throw new IllegalArgumentException("CNPJ da equipe é obrigatório");
        if(equipe.getFundacao() <= 0)
            throw new IllegalArgumentException("Fundação da equipe é obrigatório");
        if(equipe.getSede() == null || equipe.getSede().isBlank())
            throw new IllegalArgumentException("Sede da equipe é obrigatório");

        return equipeRepository.save(equipe);
    }

    @Transactional
    public void contratarTecnico(String nomeEquipe, String nomeTecnico) {

        Equipe equipe = equipeRepository.findByNome(nomeEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada: " + nomeEquipe));

        Tecnico tecnico = tecnicoRepository.findByNome(nomeTecnico)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado: " + nomeTecnico));

        if (equipe.getTecnico() != null)
            throw new IllegalArgumentException("A equipe já possui um técnico!");

        if (tecnico.getEquipe() != null)
            throw new IllegalArgumentException("O técnico já está associado a uma equipe!");

        equipe.contratar(tecnico);

        equipeRepository.save(equipe);
    }

    public Equipe demitirTecnico(EquipeNomeDTO nomeEquipe) {

        if (nomeEquipe.getNome() == null || nomeEquipe.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da equipe é obrigatório");
        }

        Equipe equipe = equipeRepository.findByNome(nomeEquipe.getNome())
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));

        Tecnico tecnico = equipe.getTecnico();

        equipe.setTecnico(null);
        tecnico.setEquipe(null);
        return equipeRepository.save(equipe);
    }

    public void contratarJogador(String nomeEquipe, String nomeJogador) {

        if (nomeEquipe == null || nomeEquipe.isBlank() || nomeJogador == null || nomeJogador.isBlank()) {
            throw new IllegalArgumentException("Nome da equipe e do jogador são obrigatórios");
        }

        Equipe equipe = equipeRepository.findByNome(nomeEquipe)
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));
        Jogador jogador = jogadorRepository.findByNome(nomeJogador)
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));

        if (jogador.getEquipe() != null) {
            throw new IllegalArgumentException("O jogador já pertence à equipe " +
                    jogador.getEquipe().getNome());
        }

        equipe.contratar(jogador);

        jogadorRepository.save(jogador);
    }

    public Equipe demitirJogador(DemitirJogadorDTO dto) {

        if (dto.getNomeEquipe() == null || dto.getNomeEquipe().isBlank()) {
            throw new IllegalArgumentException("Nome da equipe é obrigatório");
        }
        if (dto.getNomeJogador() == null || dto.getNomeJogador().isBlank()) {
            throw new IllegalArgumentException("Nome do jogador é obrigatório");
        }

        Jogador jogador = jogadorRepository.findByNome(dto.getNomeJogador())
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));
        Equipe equipe = equipeRepository.findByNome(dto.getNomeEquipe())
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));

        if (!Objects.equals(jogador.getEquipe().getNome(), equipe.getNome()))
            throw new IllegalArgumentException("O jogador não pertence a essa equipe");

        jogador.setEquipe(null);
        equipe.getLista_jogadores().remove(jogador);
        return equipeRepository.save(equipe);
    }
}
