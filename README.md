# Poor SofaScore

API REST desenvolvida em Spring Boot para gerenciamento de entidades do ecossistema futebolístico, com front-end web integrado e infraestrutura DevOps completa: containerização com Docker, orquestração com Docker Compose e pipeline de CI/CD automatizado com Jenkins.

---

## 📌 Sobre o projeto

O Poor SofaScore é uma API REST que permite operações CRUD completas e simula relações reais entre entidades esportivas (jogadores, técnicos, árbitros, equipes, federações, patrocinadores e campeonatos). Conta com um front-end estático para visualização dos dados e serve como base para estudos de arquitetura em camadas, testes automatizados e integração contínua.

---

## 🚀 Tecnologias utilizadas

- **Java 17** e **Spring Boot** (Data JPA, Web)
- **H2** — banco em memória para desenvolvimento e testes
- **HTML, CSS e JavaScript** — front-end estático servido pela aplicação
- **Mockito + JUnit 5** — testes unitários
- **RestAssured** — testes de integração e de API
- **JaCoCo** — relatório de cobertura de testes
- **K6 + JavaScript** — testes de carga e performance
- **Maven** — build (com Maven Wrapper `mvnw` incluído)
- **Docker** e **Docker Compose** — containerização e orquestração
- **Nginx** — reverse proxy
- **Redis** — serviço de cache
- **Jenkins** (em container) — pipeline CI/CD

---

## 🧱 Estrutura do projeto

```bash
poorSofaScore/
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── nginx/
│   └── default.conf                      # Configuração do reverse proxy Nginx
├── performance-tests/                    # Testes de carga K6
│   ├── jogadores-get-test.js
│   └── jogadores-post-test.js
├── src/
│   ├── main/
│   │   ├── java/br/inatel/cdg/poorSofaScore/
│   │   │   ├── PoorSofaScoreApplication.java
│   │   │   ├── config/
│   │   │   │   └── DataLoader.java
│   │   │   ├── controller/
│   │   │   │   ├── campeonatos/
│   │   │   │   │   └── CampeonatoController.java
│   │   │   │   ├── pessoa_fisica/
│   │   │   │   │   ├── ArbitroController.java
│   │   │   │   │   ├── JogadorController.java
│   │   │   │   │   └── TecnicoController.java
│   │   │   │   └── pessoa_juridica/
│   │   │   │       ├── EquipeController.java
│   │   │   │       ├── FederacaoController.java
│   │   │   │       └── PatrocinadorController.java
│   │   │   ├── bussines/
│   │   │   │   ├── campeonatos/
│   │   │   │   │   └── CampeonatoService.java
│   │   │   │   ├── pessoa_fisica/
│   │   │   │   │   ├── ArbitroService.java
│   │   │   │   │   ├── JogadorService.java
│   │   │   │   │   └── TecnicoService.java
│   │   │   │   └── pessoa_juridica/
│   │   │   │       ├── EquipeService.java
│   │   │   │       ├── FederacaoService.java
│   │   │   │       └── PatrocinadorService.java
│   │   │   └── infrastructure/
│   │   │       ├── dto/
│   │   │       │   ├── campeonatos/
│   │   │       │   │   ├── CampeonatoDTO.java
│   │   │       │   │   └── CampeonatoNomeDTO.java
│   │   │       │   ├── intermediaria/
│   │   │       │   │   ├── ContratarJogadorDTO.java
│   │   │       │   │   ├── ContratarTecnicoDTO.java
│   │   │       │   │   ├── DemitirArbitroDTO.java
│   │   │       │   │   ├── DemitirJogadorDTO.java
│   │   │       │   │   └── PatrocinioDTO.java
│   │   │       │   ├── pessoa_fisica/
│   │   │       │   │   ├── ArbitroDTO.java
│   │   │       │   │   ├── ArbitroNomeDTO.java
│   │   │       │   │   ├── JogadorDTO.java
│   │   │       │   │   ├── JogadorNomeDTO.java
│   │   │       │   │   ├── TecnicoDTO.java
│   │   │       │   │   └── TecnicoNomeDTO.java
│   │   │       │   └── pessoa_juridica/
│   │   │       │       ├── EquipeDTO.java
│   │   │       │       ├── EquipeNomeDTO.java
│   │   │       │       ├── FederacaoDTO.java
│   │   │       │       ├── FederacaoNomeDTO.java
│   │   │       │       ├── PatrocinadorDTO.java
│   │   │       │       └── PatrocinadorNomeDTO.java
│   │   │       ├── entitys/
│   │   │       │   ├── campeonatos/
│   │   │       │   │   └── Campeonato.java
│   │   │       │   ├── intermediaria/
│   │   │       │   │   ├── Patrocinio.java
│   │   │       │   │   └── interfaces/
│   │   │       │   │       └── Contratavel.java
│   │   │       │   ├── pessoa_fisica/
│   │   │       │   │   ├── Arbitro.java
│   │   │       │   │   ├── Jogador.java
│   │   │       │   │   ├── Pessoa.java
│   │   │       │   │   └── Tecnico.java
│   │   │       │   └── pessoa_juridica/
│   │   │       │       ├── Empresa.java
│   │   │       │       ├── Equipe.java
│   │   │       │       ├── Federacao.java
│   │   │       │       └── Patrocinador.java
│   │   │       └── repository/
│   │   │           ├── campeonatos/
│   │   │           │   └── CampeonatoRepository.java
│   │   │           ├── intermediaria/
│   │   │           │   └── PatrocinioRepository.java
│   │   │           ├── pessoa_fisica/
│   │   │           │   ├── ArbitroRepository.java
│   │   │           │   ├── JogadorRepository.java
│   │   │           │   └── TecnicoRepository.java
│   │   │           └── pessoa_juridica/
│   │   │               ├── EquipeRepository.java
│   │   │               ├── FederacaoRepository.java
│   │   │               └── PatrocinadorRepository.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/                   # Front-end (HTML + JS)
│   │           ├── campeonatos.html
│   │           ├── detalhes-campeonato.html
│   │           ├── detalhes-equipe.html
│   │           ├── detalhes-federacao.html
│   │           ├── equipes.html
│   │           ├── federacoes.html
│   │           ├── index.html
│   │           └── js/
│   │               ├── detalhes-camp.js
│   │               ├── detalhes-equi.js
│   │               └── detalhes-fede.js
│   └── test/java/br/inatel/cdg/poorSofaScore/
│       ├── PoorSofaScoreApplicationTests.java
│       ├── api/
│       │   ├── ArbitroApiTest.java
│       │   ├── CampeonatoApiTest.java
│       │   ├── EquipeApiTest.java
│       │   ├── JogadorApiTest.java
│       │   ├── PatrocinadorApiTest.java
│       │   └── TecnicoApiTest.java
│       ├── bussines/
│       │   ├── campeonatos/
│       │   │   └── CampeonatoServiceTest.java
│       │   ├── pessoa_fisica/
│       │   │   ├── ArbitroServiceTest.java
│       │   │   ├── JogadorServiceTest.java
│       │   │   └── TecnicoServiceTest.java
│       │   └── pessoa_juridica/
│       │       ├── EquipeServiceTest.java
│       │       ├── FederacaoServiceTest.java
│       │       └── PatrocinadorServiceTest.java
│       ├── controller/
│       │   ├── campeonatos/
│       │   │   ├── CampeonatoControllerIntegrationTest.java
│       │   │   └── CampeonatoControllerTest.java
│       │   ├── pessoa_fisica/
│       │   │   ├── ArbitroControllerIntegrationTest.java
│       │   │   ├── ArbitroControllerTest.java
│       │   │   ├── JogadorControllerIntegrationTest.java
│       │   │   ├── JogadorControllerTest.java
│       │   │   ├── TecnicoControllerIntegrationTest.java
│       │   │   └── TecnicoControllerTest.java
│       │   └── pessoa_juridica/
│       │       ├── EquipeControllerIntegrationTest.java
│       │       ├── EquipeControllerTest.java
│       │       ├── FederacaoControllerIntegrationTest.java
│       │       ├── FederacaoControllerTest.java
│       │       ├── PatrocinadorControllerIntegrationTest.java
│       │       └── PatrocinadorControllerTest.java
│       └── infrastructure/
│           ├── entitys/
│           │   ├── pessoa_fisica/
│           │   │   ├── ArbitroTest.java
│           │   │   ├── JogadorTest.java
│           │   │   └── TecnicoTest.java
│           │   └── pessoa_juridica/
│           │       ├── EquipeTest.java
│           │       └── FederacaoTest.java
│           └── repository/
│               ├── campeonatos/
│               │   └── CampeonatoRepositoryTest.java
│               ├── pessoa_fisica/
│               │   ├── ArbitroRepositoryTest.java
│               │   ├── JogadorRepositoryTest.java
│               │   └── TecnicoRepositoryTest.java
│               └── pessoa_juridica/
│                   ├── EquipeRepositoryTest.java
│                   ├── FederacaoRepositoryTest.java
│                   └── PatrocinadorRepositoryTest.java
├── Dockerfile                            # Imagem da aplicação Spring Boot
├── Dockerfile.jenkins                    # Imagem do Jenkins (Maven + Python3 + Docker CLI)
├── Dockerfile.monitor                    # Imagem do monitor de status (Python)
├── docker-compose.yml                    # Orquestração dos 5 containers
├── Jenkinsfile                           # Definição da pipeline CI/CD
├── monitor.py                            # Script de monitoramento de saúde da API
├── send_notification.py                  # Script de notificação por e-mail da pipeline
├── mvnw                                  # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                              # Maven Wrapper (Windows)
├── pom.xml
└── README.md
```

---

## ⚙️ Funcionalidades

### API REST
- CRUD completo das entidades: Jogador, Técnico, Árbitro, Equipe, Federação, Patrocinador e Campeonato.
- Busca de entidades por nome.
- Relações entre entidades: contratação de jogadores e técnicos, demissão de árbitros e jogadores, e patrocínio entre patrocinador e equipe.
- Validações de regras de negócio (campos obrigatórios, tipagem correta, etc.).

### Front-end
A aplicação serve um front-end estático (HTML + JavaScript) na pasta `src/main/resources/static/`, acessível pela raiz (`http://localhost:3000`), com páginas para:
- Listagem de **campeonatos**, **equipes** e **federações**.
- Páginas de **detalhes** de campeonato, equipe e federação (com scripts em `static/js/` que consomem a API).

---

## ✅ Pré-requisitos

- **Java JDK 17** instalado (ou usar o wrapper `mvnw` incluído)
- **Maven** instalado (opcional, o wrapper resolve)
- **Git** para clonar o repositório
- **Docker** e **Docker Compose** para subir os containers

---

## 🔧 Como rodar localmente (sem Docker)

1. Clone o repositório:
   ```bash
   git clone https://github.com/Leandrotxr/poorSofaScore.git
   cd poorSofaScore
   ```
2. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Acesse o front-end no navegador:
   ```
   http://localhost:3000
   ```
4. Exemplo de endpoint da API (listar equipes):
   ```
   http://localhost:3000/equipes
   ```

---

## 🧪 Como rodar os testes

Limpa builds antigos e executa toda a suíte de testes (unitários, integração e API):

```bash
./mvnw clean test
```

Os resultados ficam em `target/surefire-reports/`.

---

## 📊 Cobertura de testes (JaCoCo)

A cobertura de testes é medida pelo plugin **JaCoCo**, configurado no `pom.xml`. O relatório é gerado automaticamente após a execução dos testes.

Para gerar o relatório:

```bash
./mvnw clean test jacoco:report
```

O relatório HTML fica em:

```
target/site/jacoco/index.html
```

A cobertura atual do projeto é de **~91% de instruções**, atendendo ao requisito de cobertura ≥ 90%. As classes de domínio (controllers, services, entidades) ficam entre 95% e 100%; o que reduz a média são a classe principal de inicialização, a classe de configuração e interfaces — que normalmente não recebem testes diretos.

> **Observação:** alguns testes de API foram mantidos intencionalmente sinalizando comportamentos ainda não tratados pela API (ex.: validação de CPF muito curto, investimento negativo e bloqueio de método GET em rotas de criação). Por isso a pipeline pode reportar status **UNSTABLE**: a infraestrutura está funcionando e detectando essas falhas, em vez de escondê-las.

---

## ⚡ Testes de performance (K6)

Instale o K6 (https://k6.io/docs/get-started/installation/) ou via winget:

```bash
winget install k6 --source winget
```

Com a API rodando, execute:

```bash
k6 run performance-tests/jogadores-get-test.js     # teste de leitura (GET)
k6 run performance-tests/jogadores-post-test.js    # teste de escrita (POST)
```

---

## 🐳 Infraestrutura com Docker (5 containers)

Toda a infraestrutura é definida como código, orquestrada pelo `docker-compose.yml`. São **5 containers** que sobem em uma rede compartilhada (`sofascore-network`):

| Container | Origem | Porta | Função |
|---|---|---|---|
| `app` | Imagem do **Docker Hub** (`ordezin/poor-sofascore-app`) | 3000 | API Spring Boot + front-end |
| `proxy` | Imagem do **Docker Hub** (`nginx:alpine`) | 80 | Reverse proxy que encaminha as requisições para a API |
| `monitor` | **Dockerfile local** (`Dockerfile.monitor`) | — | Monitora a saúde da API a cada 10 segundos |
| `jenkins` | **Dockerfile local** (`Dockerfile.jenkins`) | 8080 | Servidor de CI/CD que executa a pipeline |
| `cache` | Imagem do **Docker Hub** (`redis:7-alpine`) | 6379 | Serviço de cache (Redis) |

### Comunicação entre containers

- **`proxy` → `app`:** o Nginx escuta na porta 80 e repassa o tráfego para `app:3000` (configurado em `nginx/default.conf`), resolvido pelo DNS interno do Docker Compose.
- **`monitor` → `app`:** o script Python (`monitor.py`) faz requisições HTTP para `http://app:3000/` verificando se a API está online.

### Volumes (persistência)

- **`jenkins-data`** → persiste toda a configuração, jobs e histórico do Jenkins (`/var/jenkins_home`), sobrevivendo a reinícios do container.
- **`nginx-logs`** → persiste os logs de acesso e erro do Nginx.

### Mix de origens (Dockerfile local + Docker Hub)

O projeto combina containers construídos a partir de **Dockerfiles locais** (`monitor` e `jenkins`) com imagens **puxadas do Docker Hub** (`app`, `proxy` e `cache`), atendendo ao requisito de mistura de origens.

### Como subir os containers

```bash
# Sobe todos os containers (constrói as imagens locais na primeira vez)
docker compose up --build -d

# Verifica o status
docker compose ps

# Acompanha os logs do monitor em tempo real
docker compose logs -f monitor
```

Acessos após subir:

```
http://localhost/         # aplicação via proxy Nginx (porta 80)
http://localhost:3000/    # aplicação direta
http://localhost:8080/    # Jenkins
```

Senha inicial do Jenkins (primeiro acesso):

```bash
docker exec poor-sofascore-jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Para parar:

```bash
docker compose down        # para os containers (preserva volumes)
docker compose down -v     # para e remove também os volumes
```

---

## 🐋 Docker Hub

A imagem da aplicação está publicada no Docker Hub:

> **https://hub.docker.com/r/ordezin/poor-sofascore-app**

```bash
docker pull ordezin/poor-sofascore-app:latest
```

---

## 🔄 Pipeline CI/CD com Jenkins

O **Jenkins roda em container** (definido em `Dockerfile.jenkins`, que já instala Maven, Python3 e o Docker CLI). Toda a pipeline é definida como código no `Jenkinsfile` — nenhuma etapa é criada pela interface gráfica do Jenkins (apenas o checkout do repositório).

### Etapas da pipeline

1. **Checkout** — baixa o código do repositório.
2. **Build** — compila o projeto (`./mvnw clean compile`).
3. **Testes Unitários** — executa os testes unitários e publica os resultados.
4. **Testes de Integração** — executa os testes de integração e de API.
5. **Cobertura de Testes** — gera o relatório JaCoCo.
6. **Empacotamento** — gera o artefato JAR (`./mvnw package`).
7. **Artefatos Jenkins** — arquiva o JAR, os relatórios de teste (Surefire) e o relatório de cobertura (JaCoCo) como artefatos no Jenkins.
8. **Notificação** — chama o script `send_notification.py` para enviar um e-mail com o resultado da execução. Em caso de falha, a notificação de falha é disparada no bloco `post`.

### Como configurar o job

1. No Jenkins: **Nova tarefa** → tipo **Pipeline**.
2. Em **Pipeline → Definition**, selecione **Pipeline script from SCM**.
3. **SCM:** Git · **Repository URL:** `https://github.com/Leandrotxr/poorSofaScore.git` · **Branch:** `*/main`.
4. **Script Path:** `Jenkinsfile`.
5. Clique em **Build Now**.

---

## 📧 Configuração de e-mail (variáveis de ambiente)

O endereço de e-mail **não está fixo (hardcoded)** em nenhum arquivo. Tanto o `Jenkinsfile` quanto o `send_notification.py` leem todos os dados de **variáveis de ambiente**, configuradas no Jenkins em **Manage Jenkins → System → Global properties → Environment variables**:

| Variável | Descrição |
|---|---|
| `SMTP_HOST` | Servidor SMTP (ex.: `smtp.gmail.com`) |
| `SMTP_PORT` | Porta SMTP (ex.: `587`) |
| `SMTP_USER` | E-mail remetente |
| `SMTP_PASS` | Senha de app do remetente (16 caracteres, gerada no Google) |
| `NOTIFICATION_EMAIL` | E-mail(s) destinatário(s) |

> Para Gmail, é necessário ativar a **Verificação em duas etapas** e gerar uma **Senha de app** — a senha normal da conta não funciona para SMTP.

---

## 🤖 Uso de IA

Conforme solicitado, esta seção declara de forma transparente o uso de Inteligência Artificial no projeto.

### Modelos utilizados
- **Claude (Anthropic)** — principal apoio na infraestrutura DevOps e revisão de configurações.
- **ChatGPT / GPT** — brainstorming e dúvidas pontuais de configuração.

### Para quê foram usados
- **`send_notification.py`** — script de notificação por e-mail da pipeline: estrutura gerada com auxílio de IA, com ajustes manuais no tratamento de erros de autenticação.
- **`monitor.py`** — script de monitoramento de status da API: desenvolvido com auxílio de IA para a lógica de verificação HTTP em loop.
- **`Dockerfile.jenkins`** — imagem do Jenkins com Maven, Python3 e Docker CLI: gerado com IA, com a adição do usuário ao grupo `docker` conferida na documentação oficial.
- **`docker-compose.yml`** — estrutura dos containers e da rede gerada com IA; o container Redis (`cache`) foi adicionado manualmente pela equipe.
- **`nginx/default.conf`** — configuração do reverse proxy: gerada com IA.
- **`Jenkinsfile`** — estrutura das etapas da pipeline: gerada com IA e ajustada (ordem do `cleanWs`, publicação de artefatos).
- **Bloco do JaCoCo no `pom.xml`** — configuração de cobertura: gerada com IA.
- **Debugging** — apoio na resolução de erros reais durante a configuração (permissão de execução do `mvnw` no Linux, conflitos de porta, autenticação SMTP).

### Exemplos reais de prompts utilizados

**Prompt 1 — Pipeline sem dado hardcoded:**
> *"Gere um Jenkinsfile com as etapas: checkout, build com Maven, testes unitários e de integração, relatório JaCoCo, empacotamento JAR, archiveArtifacts e notificação por e-mail via script Python. O e-mail NÃO pode estar hardcoded — deve vir de variável de ambiente."*
> Resposta aceita, com ajuste manual da ordem do `cleanWs()` para não apagar os artefatos antes de arquivá-los.

**Prompt 2 — Script de e-mail em Python:**
> *"Me ajude a arrumar meu script de envie e-mail via SMTP usando smtplib. Todos os dados (remetente, senha, destinatário, host, porta) devem vir de variáveis de ambiente. Deve funcionar para SUCCESS e FAILURE, mudando o assunto e o corpo."*
> Resposta aceita, com ajuste no tratamento de `SMTPAuthenticationError` para não derrubar o build em caso de erro de e-mail.

**Prompt 3 — Monitor de status:**
> *"Preciso de um script Python que fique verificando de tempos em tempos se a API em http://app:3000 está respondendo, imprimindo o status no log."*
> Resposta aceita e adaptada para o intervalo de 10 segundos e o tratamento de exceções.

**Prompt 4 — Docker Compose multi-container:**
> *"Me ajude a como continuar o meu docker-compose com a API (imagem do Docker Hub), um proxy Nginx, um container de monitoramento Python e um Jenkins, todos na mesma rede, com volumes para persistência e comunicação entre o proxy e a API."*
> Resposta aceita; o `healthcheck` do `app` e o container Redis foram adicionados manualmente depois.

### Dinâmica de uso
- A IA foi usada **individualmente** por cada integrante em sua branch antes dos Pull Requests.
- Foi usada em **pair programming** para resolver conflitos de configuração (Maven + JaCoCo, permissões do `mvnw`, setup do Jenkins).
- Foi usada para **revisar** configurações e este próprio README.

### O que NÃO foi feito por IA
- Toda a aplicação Java: entidades, serviços, controllers e repositórios.
- Todos os testes (unitários, de integração e de API com RestAssured).
- O front-end (páginas HTML e scripts JavaScript).
- As decisões de arquitetura (camadas, DTOs, relacionamentos JPA, estrutura de pacotes).
- Escolha de quais containers utilizar.
- A escolha das tecnologias e a estruturação do projeto.

---

## 👥 Autores

Projeto desenvolvido por **Leandro Teixeira, Pedro Paulo, Pedro Henrique e Luiz Otávio** como parte de estudos em Engenharia de Software (C14), e atualizado por **Leandro Teixeira, Pedro Paulo, Pedro Henrique, Luiz Otávio e Henrique Fonseca** como parte dos estudos de Qualidade, Gerência de Configuração e Evolução de Software (S07).

---

## 📄 Licença

Este projeto é para fins acadêmicos.
