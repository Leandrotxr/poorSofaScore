# Poor SofaScore  
Uma aplicação em Spring Boot para gestão de organizações futebolísticas.

---

## 🚀 Tecnologias utilizadas  
- Java 17  
- Spring Boot (Data JPA, Web)  
- Banco em memória H2 para desenvolvimento  
- Mockito + JUnit 5 para testes  
- Maven como ferramenta de build  
- Jenkins (CI/CD) para automatizar build, testes e deploy simulado  

---

## 🧱 Estrutura do projeto  
```bash
poorSofaScore/
├── src/
│   ├── main/java/br/inatel/cdg/poorSofaScore/
│   │   ├── PoorSofaScoreApplication.java
│   │   ├── config/
│   │   │   └── DataLoader.java
│   │   ├── controller/
│   │   │   ├── campeonatos/
│   │   │   │   └── CampeonatoController.java
│   │   │   ├── pessoa_fisica/
│   │   │   │   ├── ArbitroController.java
│   │   │   │   ├── JogadorController.java
│   │   │   │   └── TecnicoController.java
│   │   │   └── pessoa_juridica/
│   │   │       ├── EquipeController.java
│   │   │       ├── FederacaoController.java
│   │   │       └── PatrocinadorController.java
│   │   ├── bussines/
│   │   │   ├── campeonatos/
│   │   │   │   └── CampeonatoService.java
│   │   │   ├── pessoa_fisica/
│   │   │   │   ├── ArbitroService.java
│   │   │   │   ├── JogadorService.java
│   │   │   │   └── TecnicoService.java
│   │   │   └── pessoa_juridica/
│   │   │       ├── EquipeService.java
│   │   │       ├── FederacaoService.java
│   │   │       └── PatrocinadorService.java
│   │   ├── infrastructure/
│   │   │   ├── dto/
│   │   │   │   ├── campeonatos/
│   │   │   │   │   ├── CampeonatoDTO.java
│   │   │   │   │   └── CampeonatoNomeDTO.java
│   │   │   │   ├── intermediaria/
│   │   │   │   │   └── PatrocinioDTO.java
│   │   │   │   ├── pessoa_fisica/
│   │   │   │   │   ├── ArbitroDTO.java
│   │   │   │   │   ├── ArbitroNomeDTO.java
│   │   │   │   │   ├── JogadorDTO.java
│   │   │   │   │   ├── JogadorNomeDTO.java
│   │   │   │   │   ├── TecnicoDTO.java
│   │   │   │   │   └── TecnicoNomeDTO.java
│   │   │   │   └── pessoa_juridica/
│   │   │   │       ├── EquipeDTO.java
│   │   │   │       ├── EquipeNomeDTO.java
│   │   │   │       ├── FederacaoDTO.java
│   │   │   │       ├── FederacaoNomeDTO.java
│   │   │   │       ├── PatrocinadorDTO.java
│   │   │   │       └── PatrocinadorNomeDTO.java
│   │   │   ├── repository/
│   │   │   │   ├── campeonatos/
│   │   │   │   │   └── CampeonatoRepository.java
│   │   │   │   ├── intermediaria/
│   │   │   │   │   └── PatrocinioRepository.java
│   │   │   │   ├── pessoa_fisica/
│   │   │   │   │   ├── ArbitroRepository.java
│   │   │   │   │   ├── JogadorRepository.java
│   │   │   │   │   └── TecnicoRepository.java
│   │   │   │   └── pessoa_juridica/
│   │   │   │       ├── EquipeRepository.java
│   │   │   │       ├── FederacaoRepository.java
│   │   │   │       └── PatrocinadorRepository.java
│   │   │   └── entitys/
│   │   │       ├── campeonatos/
│   │   │       │   └── Campeonato.java
│   │   │       ├── intermediaria/
│   │   │       │   ├── Patrocinio.java
│   │   │       │   └── interfaces/
│   │   │       │       └── Contratavel.java
│   │   │       ├── pessoa_fisica/
│   │   │       │   ├── Arbitro.java
│   │   │       │   ├── Jogador.java
│   │   │       │   ├── Pessoa.java
│   │   │       │   └── Tecnico.java
│   │   │       └── pessoa_juridica/
│   │   │           ├── Empresa.java
│   │   │           ├── Equipe.java
│   │   │           ├── Federacao.java
│   │   │           └── Patrocinador.java
│   └── test/java/br/inatel/cdg/poorSofaScore/
│       ├── PoorSofaScoreApplicationTests.java
│       ├── controller/
│       ├── infrastructure/
│       ├── bussines/
│       └── entitys/
├── pom.xml
└── Jenkinsfile
```
---

## ✅ Pré-requisitos  
- Java JDK 17 instalado  
- Maven instalado ou usar o wrapper (`mvnw`) incluído  
- Git clonado do repositório  
- Jenkins instalado se for usar pipeline CI  

---

## 🔧 Como rodar localmente  
1. Clone o repositório:  
   ```bash
   git clone https://github.com/Leandrotxr/poorSofaScore.git
   cd poorSofaScore
   ```
2. Execute:
   ```bash
   ./mvnw spring-boot:run
   ```
   ou
   ```bash
   mvn spring-boot:run
   ```
3. Acesse no navegador:
   ```cpp
   http://localhost:3000
   ```
4. Use endpoints (exemplo de visualização das equipes):
   ```cpp
   http://localhost:3000/equipes
   ```

---

## 🧪 Como rodar testes
```bash
mvn test
```
Os resultados ficam em:
```bash
target/surefire-reports/
```

---

## 🔄 Pipeline (CI/CD)
Está configurado um arquivo **Jenkinsfile** na raiz do projeto que define etapas automáticas:
- Checkout do código
- Build do projeto
- Execução de testes
- Empacotamento do artefato JAR
- Deploy simulado

Para usar a Pipeline no Jenkins:
- Configure no Jenkins o job do tipo **Pipeline** apontando para o repositório
- O Jenkinsfile será detectado automaticamente
- Basta usar o **Build Now** no Jenkins

---

## 👥 Autores
Projeto desenvolvido por Leandro Teixeira, Pedro Paulo, Pedro Henrique e Luiz Otávio como parte de estudos em Engenharia de Software (C14)


