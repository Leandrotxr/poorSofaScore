package br.inatel.cdg.poorSofaScore.config;

import br.inatel.cdg.poorSofaScore.bussines.campeonatos.CampeonatoService;
import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.EquipeService;
import br.inatel.cdg.poorSofaScore.bussines.pessoa_juridica.FederacaoService;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.campeonatos.Campeonato;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Arbitro;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Jogador;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_fisica.Tecnico;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Patrocinador;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.campeonatos.CampeonatoRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.ArbitroRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.JogadorRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_fisica.TecnicoRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.EquipeRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.FederacaoRepository;
import br.inatel.cdg.poorSofaScore.infrastructure.repository.pessoa_juridica.PatrocinadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final EquipeRepository equipeRepository;
    private final TecnicoRepository tecnicoRepository;
    private final JogadorRepository jogadorRepository;
    private final PatrocinadorRepository patrocinadorRepository;
    private final FederacaoRepository federacaoRepository;
    private final ArbitroRepository arbitroRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final FederacaoService federacaoService;
    private final EquipeService equipeService;
    private final CampeonatoService campeonatoService;


    public DataLoader(EquipeRepository equipeRepository, TecnicoRepository tecnicoRepository, JogadorRepository jogadorRepository, PatrocinadorRepository patrocinadorRepository, FederacaoRepository federacaoRepository, ArbitroRepository arbitroRepository, CampeonatoRepository campeonatoRepository, EquipeService equipeService, FederacaoService federacaoService, CampeonatoService campeonatoService) {
        this.equipeRepository = equipeRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.jogadorRepository = jogadorRepository;
        this.patrocinadorRepository = patrocinadorRepository;
        this.federacaoRepository = federacaoRepository;
        this.arbitroRepository = arbitroRepository;
        this.campeonatoRepository = campeonatoRepository;
        this.equipeService = equipeService;
        this.federacaoService = federacaoService;
        this.campeonatoService = campeonatoService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if(tecnicoRepository.count() == 0) {
            Tecnico tecnico1 = new Tecnico("Mourinho","111111",62,"Português");
            Tecnico tecnico2 = new Tecnico("Guardiola","222222",54,"Espanhol");
            Tecnico tecnico3 = new Tecnico("Ancelotti","333333",65,"Italiano");
            Tecnico tecnico4 = new Tecnico("Xabi Alonso","444444",43,"Espanhol");
            Tecnico tecnico5 = new Tecnico("Filipe Luís","555555",39,"Brasileiro");

            tecnicoRepository.save(tecnico1);
            tecnicoRepository.save(tecnico2);
            tecnicoRepository.save(tecnico3);
            tecnicoRepository.save(tecnico4);
            tecnicoRepository.save(tecnico5);

            System.out.println("✅ tecnicos iniciais inseridos no banco!");
        }else {
            System.out.println("ℹ️ Banco já possui tecnicos, nenhuma inserção necessária.");
        }
        if (equipeRepository.count() == 0) {

            Equipe equipe1 = new Equipe("Fenerbahce","11111",1907,"Turquia");
            Equipe equipe2 = new Equipe("Manchester City","22222",1894,"Inglaterra");
            Equipe equipe3 = new Equipe("Real Madrid","33333",1902,"Espanha");
            Equipe equipe4 = new Equipe("Bayer Leverkusen","44444",1904,"Alemanha");
            Equipe equipe5 = new Equipe("Flamengo","55555",1895,"Brasil");

            equipeRepository.save(equipe1);
            equipeRepository.save(equipe2);
            equipeRepository.save(equipe3);
            equipeRepository.save(equipe4);
            equipeRepository.save(equipe5);



            System.out.println("✅ equipes iniciais inseridas no banco!");
        } else {
            System.out.println("ℹ️ Banco já possui equipes, nenhuma inserção necessária.");
        }
        if(jogadorRepository.count() == 0){
            // fenerbahce
            Jogador jogador01 = new Jogador("Talisca","100000", 31,"Brasileiro","Atacante");
            Jogador jogador02 = new Jogador("Skriniar","150000",30,"Eslovaco","Defensor");
            Jogador jogador03 = new Jogador("Dzeko","180000",39,"Bósnio","Atacante");
            jogadorRepository.save(jogador01);
            jogadorRepository.save(jogador02);
            jogadorRepository.save(jogador03);

            // manchester city
            Jogador jogador04 = new Jogador("De Bruyne","200000",33,"Belga","Meia");
            Jogador jogador05 = new Jogador("Doku","250000",22,"Belga","Atacante");
            Jogador jogador06 = new Jogador("Ederson","280000",31,"Brasileiro","Goleiro");
            jogadorRepository.save(jogador04);
            jogadorRepository.save(jogador05);
            jogadorRepository.save(jogador06);

            // real madrid
            Jogador jogador07 = new Jogador("Vinícius Júnior","300000",24,"Brasileiro","Atacante");
            Jogador jogador08 = new Jogador("Modric","300000",39,"Croata","Meia");
            Jogador jogador09 = new Jogador("Courtois","380000",32,"Belga","Goleiro");
            jogadorRepository.save(jogador07);
            jogadorRepository.save(jogador08);
            jogadorRepository.save(jogador09);

            // bayer leverkusen
            Jogador jogador10 = new Jogador("Frimpong","400000",24,"Holandês","Defensor");
            Jogador jogador11 = new Jogador("Wirtz","450000",22,"Alemão","Meia");
            Jogador jogador12 = new Jogador("Tah","480000",29,"Alemão","Defensor");
            jogadorRepository.save(jogador10);
            jogadorRepository.save(jogador11);
            jogadorRepository.save(jogador12);

            // flamengo
            Jogador jogador13 = new Jogador("Rossi","500000",29,"Argentino","Goleiro");
            Jogador jogador14 = new Jogador("Arrascaeta","550000",30,"Uruguaio","Meia");
            Jogador jogador15 = new Jogador("Pedro","580000",27,"Brasileiro","Atacante");
            jogadorRepository.save(jogador13);
            jogadorRepository.save(jogador14);
            jogadorRepository.save(jogador15);

            System.out.println("✅ jogadores iniciais inseridas no banco!");
        }else {
            System.out.println("ℹ️ Banco já possui jogadores, nenhuma inserção necessária.");
        }
        if(patrocinadorRepository.count() == 0){
            Patrocinador patrocinador1 = new Patrocinador("Puma","10000");
            Patrocinador patrocinador2 = new Patrocinador("Etihad Airways","20000");
            Patrocinador patrocinador3 = new Patrocinador("Fly Emirates","30000");
            Patrocinador patrocinador4 = new Patrocinador("Bayer","40000");
            Patrocinador patrocinador5 = new Patrocinador("PixBet","50000");

            patrocinadorRepository.save(patrocinador1);
            patrocinadorRepository.save(patrocinador2);
            patrocinadorRepository.save(patrocinador3);
            patrocinadorRepository.save(patrocinador4);
            patrocinadorRepository.save(patrocinador5);

            System.out.println("✅ patrocinadores iniciais inseridas no banco!");
        }else {
            System.out.println("ℹ️ Banco já possui patrocinadores, nenhuma inserção necessária.");
        }
        if(federacaoRepository.count() == 0){
            Federacao federacao1 = new Federacao("TFF", "111");
            Federacao federacao2 = new Federacao("EFA", "222");
            Federacao federacao3 = new Federacao("RFEF", "333");
            Federacao federacao4 = new Federacao("DFB", "444");
            Federacao federacao5 = new Federacao("CBF", "555");
            Federacao federacao6 = new Federacao("FIFA", "666");
            Federacao federacao7 = new Federacao("UEFA", "777");
            Federacao federacao8 = new Federacao("CONMEBOL", "888");

            federacaoRepository.save(federacao1);
            federacaoRepository.save(federacao2);
            federacaoRepository.save(federacao3);
            federacaoRepository.save(federacao4);
            federacaoRepository.save(federacao5);
            federacaoRepository.save(federacao6);
            federacaoRepository.save(federacao7);
            federacaoRepository.save(federacao8);

            System.out.println("✅ federações iniciais inseridas no banco!");
        }else {
            System.out.println("ℹ️ Banco já possui federações, nenhuma inserção necessária.");
        }
        if(arbitroRepository.count() == 0){
            Arbitro arbitro1 = new Arbitro("Bitigen","910000",42);
            Arbitro arbitro2 = new Arbitro("Oliver","920000",40);
            Arbitro arbitro3 = new Arbitro("Bengoetxa","930000",39);
            Arbitro arbitro4 = new Arbitro("Brych","940000",49);
            Arbitro arbitro5 = new Arbitro("Daronco","950000",44);
            Arbitro arbitro6 = new Arbitro("Marciniak","960000",41);
            Arbitro arbitro7 = new Arbitro("Turpin","970000",42);
            Arbitro arbitro8 = new Arbitro("Roldán","980000",45);

            arbitroRepository.save(arbitro1);
            arbitroRepository.save(arbitro2);
            arbitroRepository.save(arbitro3);
            arbitroRepository.save(arbitro4);
            arbitroRepository.save(arbitro5);
            arbitroRepository.save(arbitro6);
            arbitroRepository.save(arbitro7);
            arbitroRepository.save(arbitro8);

            System.out.println("✅ arbitros iniciais inseridas no banco!");
        }else {
            System.out.println("ℹ️ Banco já possui arbitros, nenhuma inserção necessária.");
        }
        if(campeonatoRepository.count() == 0){
            Campeonato campeonato1 = new Campeonato("SuperLig","Turquia",25000000);
            Campeonato campeonato2 = new Campeonato("Turkiye Kupasi","Turquia",10000000);
            Campeonato campeonato3 = new Campeonato("Premier League","Inglaterra",175000000);
            Campeonato campeonato4 = new Campeonato("FA Cup","Inglaterra",2000000);
            Campeonato campeonato5 = new Campeonato("Carabao Cup","Inglaterra",1000000);
            Campeonato campeonato6 = new Campeonato("LaLiga","Espanha",143000000);
            Campeonato campeonato7 = new Campeonato("Copa Del Rey","Espanha",2000000);
            Campeonato campeonato8 = new Campeonato("Bundesliga","Alemanha",76000000);
            Campeonato campeonato9 = new Campeonato("DFB Pokal","Alemanha",1000000);
            Campeonato campeonato10 = new Campeonato("Brasileirão","Brasil",9000000);
            Campeonato campeonato11 = new Campeonato("Copa do Brasil","Brasil",20000000);
            Campeonato campeonato12 = new Campeonato("Mundial de Clubes","Estados Unidos",125000000);
            Campeonato campeonato13 = new Campeonato("Champions League","Europa",127000000);
            Campeonato campeonato14 = new Campeonato("Europa League","Europa",14000000);
            Campeonato campeonato15 = new Campeonato("Libertadores","América do Sul",24000000);
            Campeonato campeonato16 = new Campeonato("Sulamericana","América do Sul",5000000);

            campeonatoRepository.save(campeonato1);
            campeonatoRepository.save(campeonato2);
            campeonatoRepository.save(campeonato3);
            campeonatoRepository.save(campeonato4);
            campeonatoRepository.save(campeonato5);
            campeonatoRepository.save(campeonato6);
            campeonatoRepository.save(campeonato7);
            campeonatoRepository.save(campeonato8);
            campeonatoRepository.save(campeonato9);
            campeonatoRepository.save(campeonato10);
            campeonatoRepository.save(campeonato11);
            campeonatoRepository.save(campeonato12);
            campeonatoRepository.save(campeonato13);
            campeonatoRepository.save(campeonato14);
            campeonatoRepository.save(campeonato15);
            campeonatoRepository.save(campeonato16);

            System.out.println("✅ campeonatos iniciais inseridas no banco!");
        }else {
            System.out.println("ℹ️ Banco já possui campeonatos, nenhuma inserção necessária.");
        }
        if (equipeRepository.count() > 0 && jogadorRepository.count() > 0 && tecnicoRepository.count() > 0 && patrocinadorRepository.count() > 0 && arbitroRepository.count() > 0 && federacaoRepository.count() > 0) {

            // Busca do banco (garante IDs corretos)
            Equipe fener = equipeRepository.findByNome("Fenerbahce")
                    .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));
            Equipe city = equipeRepository.findByNome("Manchester City")
                    .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));
            Equipe madrid = equipeRepository.findByNome("Real Madrid")
                    .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));
            Equipe lever = equipeRepository.findByNome("Bayer Leverkusen")
                    .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));
            Equipe fla = equipeRepository.findByNome("Flamengo")
                    .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));

            Tecnico mourinho = tecnicoRepository.findByNome("Mourinho")
                    .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
            Tecnico pep = tecnicoRepository.findByNome("Guardiola")
                    .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
            Tecnico ancelotti = tecnicoRepository.findByNome("Ancelotti")
                    .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
            Tecnico xabi = tecnicoRepository.findByNome("Xabi Alonso")
                    .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
            Tecnico filipe = tecnicoRepository.findByNome("Filipe Luís")
                    .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

            // Patrocinadores
            Patrocinador puma = patrocinadorRepository.findByNome("Puma")
                    .orElseThrow(() -> new RuntimeException("Patrocinador não encontrado"));
            Patrocinador etihad = patrocinadorRepository.findByNome("Etihad Airways")
                    .orElseThrow(() -> new RuntimeException("Patrocinador não encontrado"));
            Patrocinador fly = patrocinadorRepository.findByNome("Fly Emirates")
                    .orElseThrow(() -> new RuntimeException("Patrocinador não encontrado"));
            Patrocinador bayer = patrocinadorRepository.findByNome("Bayer")
                    .orElseThrow(() -> new RuntimeException("Patrocinador não encontrado"));
            Patrocinador pix = patrocinadorRepository.findByNome("PixBet")
                    .orElseThrow(() -> new RuntimeException("Patrocinador não encontrado"));

            // Federações
            Federacao tff = federacaoRepository.findByNome("TFF")
                    .orElseThrow(() -> new RuntimeException("Federação não encontrada"));
            Federacao efa = federacaoRepository.findByNome("EFA")
                    .orElseThrow(() -> new RuntimeException("Federação não encontrada"));
            Federacao rfef = federacaoRepository.findByNome("RFEF")
                    .orElseThrow(() -> new RuntimeException("Federação não encontrada"));
            Federacao dfb = federacaoRepository.findByNome("DFB")
                    .orElseThrow(() -> new RuntimeException("Federação não encontrada"));
            Federacao cbf = federacaoRepository.findByNome("CBF")
                    .orElseThrow(() -> new RuntimeException("Federação não encontrada"));
            Federacao fifa = federacaoRepository.findByNome("FIFA")
                    .orElseThrow(() -> new RuntimeException("Federação não encontrada"));
            Federacao uefa = federacaoRepository.findByNome("UEFA")
                    .orElseThrow(() -> new RuntimeException("Federação não encontrada"));
            Federacao conmebol = federacaoRepository.findByNome("CONMEBOL")
                    .orElseThrow(() -> new RuntimeException("Federação não encontrada"));

            // Árbitros
            Arbitro bitigen = arbitroRepository.findByNome("Bitigen")
                    .orElseThrow(() -> new RuntimeException("Árbitro não encontrado"));
            Arbitro oliver = arbitroRepository.findByNome("Oliver")
                    .orElseThrow(() -> new RuntimeException("Árbitro não encontrado"));
            Arbitro bengoetxa = arbitroRepository.findByNome("Bengoetxa")
                    .orElseThrow(() -> new RuntimeException("Árbitro não encontrado"));
            Arbitro brych = arbitroRepository.findByNome("Brych")
                    .orElseThrow(() -> new RuntimeException("Árbitro não encontrado"));
            Arbitro daronco = arbitroRepository.findByNome("Daronco")
                    .orElseThrow(() -> new RuntimeException("Árbitro não encontrado"));

            // =====================
            // ⚽ CONTRATAÇÕES
            // =====================

            // Técnicos
            fener.contratar(mourinho);
            city.contratar(pep);
            madrid.contratar(ancelotti);
            lever.contratar(xabi);
            fla.contratar(filipe);

            // Jogadores
            fener.contratar(jogadorRepository.findByNome("Talisca")
                    .orElseThrow(() -> new RuntimeException("Jogador não encontrado")));
            fener.contratar(jogadorRepository.findByNome("Skriniar")
                    .orElseThrow(() -> new RuntimeException("Jogador não encontrado")));
            city.contratar(jogadorRepository.findByNome("De Bruyne")
                    .orElseThrow(() -> new RuntimeException("Jogador não encontrado")));
            madrid.contratar(jogadorRepository.findByNome("Vinícius Júnior")
                    .orElseThrow(() -> new RuntimeException("Jogador não encontrado")));
            fla.contratar(jogadorRepository.findByNome("Pedro")
                    .orElseThrow(() -> new RuntimeException("Jogador não encontrado")));

            equipeService.contratarPatrocinio(fener, puma, 1000);
            equipeService.contratarPatrocinio(city, etihad, 2000);
            equipeService.contratarPatrocinio(city, puma, 3000);
            equipeService.contratarPatrocinio(madrid, fly, 4000);
            equipeService.contratarPatrocinio(lever, bayer, 5000);
            equipeService.contratarPatrocinio(fla, pix, 6000);

            tff.contratar(bitigen);
            efa.contratar(oliver);
            rfef.contratar(bengoetxa);
            dfb.contratar(brych);
            cbf.contratar(daronco);

            Campeonato superLig = campeonatoRepository.findByNome("SuperLig").orElseThrow();
            Campeonato turkiyeKupasi = campeonatoRepository.findByNome("Turkiye Kupasi").orElseThrow();
            Campeonato premier = campeonatoRepository.findByNome("Premier League").orElseThrow();
            Campeonato faCup = campeonatoRepository.findByNome("FA Cup").orElseThrow();
            Campeonato carabao = campeonatoRepository.findByNome("Carabao Cup").orElseThrow();
            Campeonato laLiga = campeonatoRepository.findByNome("LaLiga").orElseThrow();
            Campeonato copaDelRey = campeonatoRepository.findByNome("Copa Del Rey").orElseThrow();
            Campeonato bundesliga = campeonatoRepository.findByNome("Bundesliga").orElseThrow();
            Campeonato dfbPokal = campeonatoRepository.findByNome("DFB Pokal").orElseThrow();
            Campeonato brasileirao = campeonatoRepository.findByNome("Brasileirão").orElseThrow();
            Campeonato copaDoBrasil = campeonatoRepository.findByNome("Copa do Brasil").orElseThrow();
            Campeonato mundial = campeonatoRepository.findByNome("Mundial de Clubes").orElseThrow();
            Campeonato champions = campeonatoRepository.findByNome("Champions League").orElseThrow();
            Campeonato europa = campeonatoRepository.findByNome("Europa League").orElseThrow();
            Campeonato libertadores = campeonatoRepository.findByNome("Libertadores").orElseThrow();
            Campeonato sulamericana = campeonatoRepository.findByNome("Sulamericana").orElseThrow();


            // 🌍 Federações Nacionais
            federacaoService.adcionarCampeonato(tff, superLig);
            federacaoService.adcionarCampeonato(tff, turkiyeKupasi);

            federacaoService.adcionarCampeonato(efa, premier);
            federacaoService.adcionarCampeonato(efa, faCup);
            federacaoService.adcionarCampeonato(efa, carabao);

            federacaoService.adcionarCampeonato(rfef, laLiga);
            federacaoService.adcionarCampeonato(rfef, copaDelRey);

            federacaoService.adcionarCampeonato(dfb, bundesliga);
            federacaoService.adcionarCampeonato(dfb, dfbPokal);

            federacaoService.adcionarCampeonato(cbf, brasileirao);
            federacaoService.adcionarCampeonato(cbf, copaDoBrasil);

            // 🌎 Federações internacionais
            federacaoService.adcionarCampeonato(fifa, mundial);
            federacaoService.adcionarCampeonato(uefa, champions);
            federacaoService.adcionarCampeonato(uefa, europa);
            federacaoService.adcionarCampeonato(conmebol, libertadores);
            federacaoService.adcionarCampeonato(conmebol, sulamericana);

            // Turquia
            campeonatoService.adicionarCampeonato(fener, superLig);
            campeonatoService.adicionarCampeonato(fener, turkiyeKupasi);

            // Inglaterra
            campeonatoService.adicionarCampeonato(city, premier);
            campeonatoService.adicionarCampeonato(city, faCup);
            campeonatoService.adicionarCampeonato(city, carabao);

            // Espanha
            campeonatoService.adicionarCampeonato(madrid, laLiga);
            campeonatoService.adicionarCampeonato(madrid, copaDelRey);

            // Alemanha
            campeonatoService.adicionarCampeonato(lever, bundesliga);
            campeonatoService.adicionarCampeonato(lever, dfbPokal);

            // Brasil
            campeonatoService.adicionarCampeonato(fla, brasileirao);
            campeonatoService.adicionarCampeonato(fla, copaDoBrasil);

            // 🌍 Competições internacionais
            campeonatoService.adicionarCampeonato(fener, europa);
            campeonatoService.adicionarCampeonato(city, champions);
            campeonatoService.adicionarCampeonato(madrid, champions);
            campeonatoService.adicionarCampeonato(lever, europa);
            campeonatoService.adicionarCampeonato(fla, libertadores);
            campeonatoService.adicionarCampeonato(fla, mundial);

            // =====================
            // 💾 SALVAR NO BANCO
            // =====================
            equipeRepository.save(fener);
            equipeRepository.save(city);
            equipeRepository.save(madrid);
            equipeRepository.save(lever);
            equipeRepository.save(fla);

            federacaoRepository.save(tff);
            federacaoRepository.save(efa);
            federacaoRepository.save(rfef);
            federacaoRepository.save(dfb);
            federacaoRepository.save(cbf);
            federacaoRepository.save(fifa);
            federacaoRepository.save(uefa);
            federacaoRepository.save(conmebol);

            System.out.println("🤝 Contratações e patrocínios realizados com sucesso!");
        }
    }
}
