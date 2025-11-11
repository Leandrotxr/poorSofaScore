# poorSofaScore  
Uma aplicação demo em Spring Boot para gestão de patrocinadores, equipes e jogadores — com foco em aprender integração contínua (CI) e entrega contínua (CD).

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
