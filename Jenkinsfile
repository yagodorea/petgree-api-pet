pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'pwd'
		sh 'ls -la'
		sh 'id'
		sh 'wget www.uol.com.br'
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}
