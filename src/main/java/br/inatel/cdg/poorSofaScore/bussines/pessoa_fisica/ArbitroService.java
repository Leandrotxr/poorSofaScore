package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.ArbitroNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.ArbitroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArbitroService {

    @Autowired
    private ArbitroRepository arbitroRepository;

    public List<ArbitroDTO> listarArbitros() {
        return arbitroRepository.findAll().stream()
                .map(arbitro -> ArbitroDTO.builder()
                        .nome(arbitro.getNome())
                        .idade(arbitro.getIdade())
                        .federacao(arbitro.getFederacao() != null ? arbitro.getFederacao().getNome() : null)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<ArbitroNomeDTO> listarNome() {
        return arbitroRepository.findAll()
                .stream()
                .map(arbitro -> new ArbitroNomeDTO(arbitro.getNome()))
                .collect(Collectors.toList());
    }

    public ArbitroDTO buscarArbitroPorNome(String nome) {
        Arbitro arbitro = arbitroRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Arbitro não encontrada: " + nome));

        return ArbitroDTO.builder()
                .nome(arbitro.getNome())
                .idade(arbitro.getIdade())
                .federacao(arbitro.getFederacao() != null ? arbitro.getFederacao().getNome() : null)
                .build();
    }

    public Arbitro adicionarArbitro(Arbitro arbitro) {
        if(arbitro.getNome() == null || arbitro.getNome().isBlank())
            throw new IllegalArgumentException("Nome do arbitro é obrigatório");
        if(arbitro.getCpf() == null || arbitro.getCpf().isBlank())
            throw new IllegalArgumentException("CPF do arbitro é obrigatório");
        if(arbitro.getIdade() <= 0)
            throw new IllegalArgumentException("Idade do arbitro é obrigatório");

        return arbitroRepository.save(arbitro);
    }
}
