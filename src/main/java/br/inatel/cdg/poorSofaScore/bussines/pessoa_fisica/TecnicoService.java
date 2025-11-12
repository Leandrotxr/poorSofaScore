package br.inatel.cdg.poorSofaScore.bussines.pessoa_fisica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_fisica.TecnicoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public List<TecnicoDTO> listarTecnicos() {
        return tecnicoRepository.findAll().stream()
                .map(tecnico -> TecnicoDTO.builder()
                        .nome(tecnico.getNome())
                        .idade(tecnico.getIdade())
                        .nacionalidade(tecnico.getNacionalidade())
                        .equipe(tecnico.getEquipe() != null ? tecnico.getEquipe().getNome() : null)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<TecnicoNomeDTO> listarNome() {
        return tecnicoRepository.findAll()
                .stream()
                .map(tecnico -> new TecnicoNomeDTO(tecnico.getNome()))
                .collect(Collectors.toList());
    }

    public TecnicoDTO buscarTecnicoPorNome(String nome) {
        Tecnico tecnico = tecnicoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Tecnico não encontrada: " + nome));

        return TecnicoDTO.builder()
                .nome(tecnico.getNome())
                .idade(tecnico.getIdade())
                .nacionalidade(tecnico.getNacionalidade())
                .equipe(tecnico.getEquipe() != null ? tecnico.getEquipe().getNome() : null)
                .build();
    }

    public Tecnico adicionarTecnico(Tecnico tecnico) {
        if(tecnico.getNome() == null || tecnico.getNome().isBlank())
            throw new IllegalArgumentException("Nome do tecnico é obrigatório");
        if(tecnico.getCpf() == null || tecnico.getCpf().isBlank())
            throw new IllegalArgumentException("CPF do tecnico é obrigatório");
        if(tecnico.getIdade() <= 0)
            throw new IllegalArgumentException("Idade do tecnico é obrigatório");
        if(tecnico.getNacionalidade() == null || tecnico.getNacionalidade().isBlank())
            throw new IllegalArgumentException("Nacionalidade do tecnico é obrigatório");

        return tecnicoRepository.save(tecnico);
    }
}
