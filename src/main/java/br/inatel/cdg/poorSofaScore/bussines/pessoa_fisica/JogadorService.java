package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.JogadorNomeDTO;
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

    public Jogador adicionarJogador(Jogador jogador) {
        if(jogador.getNome() == null || jogador.getNome().isBlank())
            throw new IllegalArgumentException("Nome do jogador é obrigatório");
        if(jogador.getCpf() == null || jogador.getCpf().isBlank())
            throw new IllegalArgumentException("CPF do jogador é obrigatório");
        if(jogador.getIdade() <= 0)
            throw new IllegalArgumentException("Idade do jogador é obrigatório");
        if(jogador.getNacionalidade() == null || jogador.getNacionalidade().isBlank())
            throw new IllegalArgumentException("Nacionalidade do jogador é obrigatório");
        if(jogador.getPosicao() == null || jogador.getPosicao().isBlank())
            throw new IllegalArgumentException("Nacionalidade do jogador é obrigatório");

        return jogadorRepository.save(jogador);
    }
}
