pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK'
    }

    environment {
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=false'
        MAVEN_HOME = tool 'Maven'
        JAVA_HOME = tool 'JDK'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
        ansiColor('xterm')
    }

    stages {
        stage('Checkout') {
            steps {
                echo "📦 Baixando código do repositório..."
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "🏗️ Compilando o projeto..."
                sh "mvn -B clean compile"
            }
        }

        stage('Test') {
            steps {
                echo "🧪 Executando testes unitários..."
                sh "mvn -B test"
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Quality') {
            steps {
                echo "🔍 Analisando qualidade de código e cobertura de testes..."
                sh "mvn -B verify -DskipITs"
            }
            post {
                always {
                    echo "📊 Publicando relatório de cobertura (JaCoCo)..."
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java',
                        inclusionPattern: '**/*.class',
                        exclusionPattern: '**/*Test*'
                    )
                }
            }
        }

        stage('Package') {
            steps {
                echo "📦 Empacotando artefato JAR..."
                sh "mvn -B package -DskipTests"
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Deploy (Simulado)') {
            steps {
                echo "🚀 Simulando deploy do arquivo JAR..."
                sh "ls -lh target/*.jar"
            }
        }
    }

    post {
        success {
            echo "✅ Build finalizado com sucesso! Qualidade e testes verificados!"
        }
        failure {
            echo "❌ Falha na pipeline. Verifique os logs de erro."
        }
        always {
            echo "📊 Pipeline finalizada — resultado armazenado no histórico do Jenkins."
        }
    }
}
