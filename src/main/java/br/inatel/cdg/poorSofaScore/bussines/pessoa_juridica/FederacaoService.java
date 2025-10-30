package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.FederacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FederacaoService {

    @Autowired
    private FederacaoRepository federacaoRepository;

    public void adcionarCampeonato(Federacao federacao, Campeonato campeonato){
        federacao.getLista_campeonato().add(campeonato);
        campeonato.setFederacao(federacao);
    }

    public List<FederacaoDTO> listarFederacao() {
        return federacaoRepository.findAll().stream()
                .map(federacao -> FederacaoDTO.builder()
                        .nome(federacao.getNome())
                        .arbitros(federacao.getLista_arbitro().stream()
                                .map(Arbitro::getNome)
                                .collect(Collectors.toList()))
                        .campeonatos(federacao.getLista_campeonato().stream()
                                .map(Campeonato::getNome)
                                .collect(Collectors.toList()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<FederacaoNomeDTO> listarNome() {
        return federacaoRepository.findAll()
                .stream()
                .map(federacao -> new FederacaoNomeDTO(federacao.getNome()))
                .collect(Collectors.toList());
    }

    public FederacaoDTO buscarFederacaoPorNome(String nome) {
        Federacao federacao = federacaoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Federação não encontrada: " + nome));

        return FederacaoDTO.builder()
                .nome(federacao.getNome())
                .arbitros(federacao.getLista_arbitro().stream()
                        .map(Arbitro::getNome)
                        .collect(Collectors.toList()))
                .campeonatos(federacao.getLista_campeonato().stream()
                        .map(Campeonato::getNome)
                        .collect(Collectors.toList()))
                .build();
    }
}
