package br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica;

import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.dto.pessoa_juridica.FederacaoNomeDTO;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.ArbitroRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.FederacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FederacaoService {

    private final FederacaoRepository federacaoRepository;
    private final ArbitroRepository arbitroRepository;

    public FederacaoService(FederacaoRepository federacaoRepository, ArbitroRepository arbitroRepository) {
        this.federacaoRepository = federacaoRepository;
        this.arbitroRepository = arbitroRepository;
    }

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

    public Federacao adicionarFederacao(Federacao federacao) {
        if(federacao.getNome() == null || federacao.getNome().isBlank())
            throw new IllegalArgumentException("Nome da federacao é obrigatório");
        if(federacao.getCnpj() == null || federacao.getCnpj().isBlank())
            throw new IllegalArgumentException("CNPJ da federacao é obrigatório");

        return federacaoRepository.save(federacao);
    }

    public void contratarArbitro(String nomeFederacao, String NomeArbitro) {

        if (nomeFederacao == null || nomeFederacao.isBlank() || NomeArbitro == null || NomeArbitro.isBlank()) {
            throw new IllegalArgumentException("Nome da federacao e do arbitro são obrigatórios");
        }

        Federacao federacao = federacaoRepository.findByNome(nomeFederacao)
                .orElseThrow(() -> new IllegalArgumentException("Federacao não encontrada"));
        Arbitro arbitro = arbitroRepository.findByNome(NomeArbitro)
                .orElseThrow(() -> new IllegalArgumentException("Arbitro não encontrado"));

        if (arbitro.getFederacao() != null) {
            throw new IllegalArgumentException("O arbitro já pertence à federacao " +
                    arbitro.getFederacao().getNome());
        }

        federacao.contratar(arbitro);
        arbitro.setFederacao(federacao);
        arbitroRepository.save(arbitro);
    }
}
