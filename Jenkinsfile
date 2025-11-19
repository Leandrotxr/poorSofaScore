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
        }
        failure {
            echo '❌ Falha na pipeline. Verifique os logs.'
        }
    }
}
