package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    public List<JogadorDTO> listarJogadores() {
        return jogadorRepository.findAll().stream()
                .map(jogador -> JogadorDTO.builder()
                        .nome(jogador.getNome())
                        .idade(jogador.getIdade())
                        .nacionalidade(jogador.getNacionalidade())
                        .posicao(jogador.getPosicao())
                        .equipe(jogador.getEquipe() != null ? jogador.getEquipe().getNome() : null)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<JogadorNomeDTO> listarNome() {
        return jogadorRepository.findAll()
                .stream()
                .map(jogador -> new JogadorNomeDTO(jogador.getNome()))
                .collect(Collectors.toList());
    }

    public JogadorDTO buscarJogadorPorNome(String nome) {
        Jogador jogador = jogadorRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrada: " + nome));

        return JogadorDTO.builder()
                .nome(jogador.getNome())
                .idade(jogador.getIdade())
                .nacionalidade(jogador.getNacionalidade())
                .posicao(jogador.getPosicao())
                .equipe(jogador.getEquipe() != null ? jogador.getEquipe().getNome() : null)
                .build();
    }
}
