pipeline {
    agent any
    triggers { pollSCM('* * * *') }
    stages {
        // stage('Checkout') {
        //     steps {
        //         git branch: 'main', url: 'https://github.com/dovdembin/fisrt.git'
        //     }
        // }
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
