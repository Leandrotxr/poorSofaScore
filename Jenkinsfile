pipeline {
    agent any

    environment {
        // E-mail configurado como variavel de ambiente no Jenkins (Manage Jenkins > System)
        NOTIFICATION_EMAIL = "${env.NOTIFICATION_EMAIL}"
        SMTP_HOST          = "${env.SMTP_HOST}"
        SMTP_PORT          = "${env.SMTP_PORT}"
        SMTP_USER          = "${env.SMTP_USER}"
        SMTP_PASS          = "${env.SMTP_PASS}"
    }

    stages {

        stage('Checkout') {
            steps {
                echo '=== Baixando codigo do repositorio ==='
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '=== Compilando o projeto ==='
                sh './mvnw clean compile -B'
            }
            post {
                failure {
                    echo 'Falha na compilacao. Verifique o codigo-fonte.'
                }
            }
        }

        stage('Testes Unitarios') {
            steps {
                echo '=== Executando Testes Unitarios ==='
                sh './mvnw -Dtest="*Test,*ServiceTest,*EntityTest,*RepositoryTest" -DfailIfNoTests=false test -B'
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Testes de Integracao') {
            steps {
                echo '=== Executando Testes de Integracao ==='
                sh './mvnw -Dtest="*IntegrationTest,*ApiTest" -DfailIfNoTests=false test -B'
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Cobertura de Testes') {
            steps {
                echo '=== Gerando relatorio de cobertura JaCoCo ==='
                sh './mvnw clean test jacoco:report -B'
            }
        }

        stage('Empacotamento') {
            steps {
                echo '=== Gerando artefato JAR ==='
                sh './mvnw package -DskipTests -B'
                sh 'ls -lh target/*.jar'
            }
        }

        stage('Artefatos Jenkins') {
            steps {
                echo '=== Arquivando artefatos no Jenkins ==='
                archiveArtifacts(
                    artifacts: 'target/*.jar',
                    fingerprint: true,
                    allowEmptyArchive: false
                )
                archiveArtifacts(
                    artifacts: 'target/surefire-reports/**',
                    allowEmptyArchive: true
                )
                archiveArtifacts(
                    artifacts: 'target/site/jacoco/**',
                    allowEmptyArchive: true
                )
            }
        }

        stage('Notificacao') {
            steps {
                echo '=== Enviando notificacao de sucesso por e-mail ==='
                withEnv(['BUILD_STATUS=SUCCESS']) {
                    sh 'python3 send_notification.py || true'
                }
            }
        }
    }

    post {
        failure {
            echo '=== Pipeline falhou — enviando notificacao de falha ==='
            withEnv(['BUILD_STATUS=FAILURE']) {
                sh 'python3 send_notification.py || true'
            }
        }
        always {
            echo '=== Pipeline finalizada. Limpando workspace... ==='
            cleanWs()
        }
    }
}