pipeline {
    agent any
    triggers { pollSCM('* * * *') }
    stages {
        stage('Build') {
            steps {
                sh "ls -l"
            }
        }

        post {
            always {
                archiveArtifacts artifacts: '*.html'
            }
        }
    }
}
