# poorSofaScore  
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
Maven standard layout  
├─ src/main/java → classes de entidade, repositório, serviço e controller  
├─ src/test/java → testes unitários com Mockito e JUnit 5  
└─ pom.xml → dependências e plugins  

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


