pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📦 Baixando código do repositório...'
                checkout scm
            }
        }

        stage('Validate') {
            steps {
                echo '🔎 Validando projeto...'
                sh 'mvn validate'
            }
            post {
                  failure {
                        echo '❌ Falha na validação. Verifique o pom.xml e dependências.'
                  }
            }
        }

        stage('Build') {
            steps {
                echo '🏗️ Compilando o projeto...'
                sh 'mvn clean package'
            }
        }

        stage('Tests (Parallel)') {
            parallel {

                stage('Unit Tests') {
                    steps {
                        echo '🧪 Executando TESTES UNITÁRIOS...'
                        sh 'mvn -Dtest=*Test test'
                    }
                }

                stage('Integration Tests') {
                    steps {
                        echo '🔗 Executando TESTES DE INTEGRAÇÃO...'
                        sh 'mvn -Dtest=*IntegrationTest test'
                    }
                }
            }
        }

        stage('Package') {
            steps {
                echo '📦 Gerando artefato JAR...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy (simulado)') {
            steps {
                echo '🚀 Simulando deploy do arquivo JAR...'
                sh 'ls -lh target/*.jar'
            }
        }
    }

	post {
		success {
			echo '✅ Build finalizado com sucesso!'
			emailext (
				subject: "✅ SUCESSO: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
				body: "A pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} foi concluída com SUCESSO! \n\nDetalhes: ${env.BUILD_URL}",
				to: 'luizotavio.paiva07@gmail.com',
				mimeType: 'text/plain'
			)
		}
		failure {
			echo '❌ Falha na pipeline. Verifique os logs.'
			emailext (
				subject: "❌ FALHA: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
				body: "A pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} FALHOU! \n\nVerifique os logs: ${env.BUILD_URL}/console",
				to: 'luizotavio.paiva07@gmail.com',
				mimeType: 'text/plain'
			)
		}
		// Adiciona uma etapa para limpar arquivos de build após a conclusão
		always {
			sh 'mvn clean'
		}
	}
}
