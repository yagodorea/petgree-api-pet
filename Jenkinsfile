pipeline {
    agent {
        docker {
            image 'maven:3.5.4-jdk-8-slim'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn clean package' 
            }
        }
    }
}
