pipeline {
    agent any
    environment {
        GIT_URL = "https://github.com/KIM-KYOUNG-OH/ChatBot"
//         DEPLOY_SERVER_ID = credentials("deployServerId")
//         DEPLOY_SERVER_PASSWORD = credentials("deployServerPassword")
//         SECRET_KEY = credentials('SECRET_KEY')
//         DB_USERNAME = credentials('DB_USERNAME')
//         DB_PASSWORD = credentials('DB_PASSWORD')
//         DB_URL = credentials('DB_URL')
//         DB_PORT = credentials('DB_PORT')
    }

    stages {
        stage("Pull") {
            git url: "${GIT_URL}", branch: "fix-jenkins-pipeline", poll: true, changelog: true
        }

//         stage("Build") {
//             steps {
//                 sh "./gradlew clean build"
//             }
//         }

//         stage("Send Jar") {
//             steps {
//                 sh 'sshpass -p $DEPLOY_SERVER_PASSWORD ssh -T -p 12308 $DEPLOY_SERVER_ID@106.10.59.248 rm -rf build/fire_inform-0.0.1-SNAPSHOT.jar'
//                 sh 'sshpass -p $DEPLOY_SERVER_PASSWORD scp -P 12308 -o StrictHostKeyChecking=no /var/lib/jenkins/workspace/pipeline/build/libs/fire_inform-0.0.1-SNAPSHOT.jar $DEPLOY_SERVER_ID@106.10.59.248:build'
//             }
//         }
//
//         stage("Send Shell Script") {
//             steps {
//                 sh 'sshpass -p $DEPLOY_SERVER_PASSWORD ssh -T -p 12308 $DEPLOY_SERVER_ID@106.10.59.248 rm -rf build/deploy.sh'
//                 sh 'sshpass -p $DEPLOY_SERVER_PASSWORD scp -P 12308 -o StrictHostKeyChecking=no /var/lib/jenkins/workspace/pipeline/deploy.sh $DEPLOY_SERVER_ID@106.10.59.248:build'
//             }
//         }
//
//         stage("Connect Deploy Server") {
//             steps {
//                 sh 'sshpass -p $DEPLOY_SERVER_PASSWORD ssh -T -p 12308 $DEPLOY_SERVER_ID@106.10.59.248 sh build/deploy.sh'
//             }
//         }
    }
}