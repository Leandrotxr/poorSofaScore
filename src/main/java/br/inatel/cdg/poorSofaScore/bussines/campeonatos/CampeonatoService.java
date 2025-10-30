package br.inatel.cdg.poorSofaScore.bussines.campeonatos;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.campeonatos.CampeonatoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos.CampeonatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampeonatoService {

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    public CampeonatoService(CampeonatoRepository campeonatoRepository) {
        this.campeonatoRepository = campeonatoRepository;
    }

    public void adicionarCampeonato(Equipe equipe, Campeonato campeonato) {
        equipe.getLista_campeonatos().add(campeonato);
        campeonato.getEquipes().add(equipe);
    }

    public List<CampeonatoDTO> listarCampeonatos() {
        return campeonatoRepository.findAll().stream()
                .map(campeonato -> CampeonatoDTO.builder()
                        .nome(campeonato.getNome())
                        .local(campeonato.getLocal())
                        .premio(campeonato.getPremio())
                        .federacao(campeonato.getFederacao() != null ? campeonato.getFederacao().getNome() : null)
                        .equipes(campeonato.getEquipes().stream()
                                .map(Equipe::getNome)
                                .collect(Collectors.toList()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<CampeonatoNomeDTO> listarNome() {
        return campeonatoRepository.findAll()
                .stream()
                .map(campeonato -> new CampeonatoNomeDTO(campeonato.getNome()))
                .collect(Collectors.toList());
    }

    public CampeonatoDTO buscarCampeonatoPorNome(String nome) {
        Campeonato campeonato = campeonatoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrada: " + nome));

        return CampeonatoDTO.builder()
                .nome(campeonato.getNome())
                .local(campeonato.getLocal())
                .premio(campeonato.getPremio())
                .federacao(campeonato.getFederacao() != null ? campeonato.getFederacao().getNome() : null)
                .equipes(campeonato.getEquipes().stream()
                        .map(Equipe::getNome)
                        .collect(Collectors.toList()))
                .build();
    }

    public void adicionarCampeonato(Campeonato campeonato) {
        campeonatoRepository.save(campeonato);
    }
}
