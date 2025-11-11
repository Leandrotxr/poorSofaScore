pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
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

        stage('Test') {
            steps {
                echo '🧪 Executando testes...'
                sh 'mvn test'
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
