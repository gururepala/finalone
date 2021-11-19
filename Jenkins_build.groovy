//Descriptive pipeline
pipeline{
    agent any
    environment {
        BRANCH = "${env.BRANCH_NAME}"
    }
    stages{
        stage("Cloning the code from GITHUB URL") {
            steps {
                println "Using the URL copied from GIT HUB repository we are cloning the code"
                //checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, userRemoteConfigs: [[ url: 'https://github.com/gururepala/boxfuse-sample-java-war-hello.git']]])
                git branch: "${BRANCH_NAME}",
                url: 'https://github.com/gururepala/boxfuse-sample-java-war-hello.git'
                sh """
                ls -al
                """
            }        
        }
        stage("Building the code") {
            steps {
                println "Copied GIT repository code will be builded using maven command"
                sh """
                mvn clean package
                echo "$BUILD_NUMBER"
                ls -al target/
                """
            }
        }
        stage("Builded code will be copied to artifacts") {
            steps {
                println "Builded code will be uploaded to S3 bucket"
                sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://mydeployedprojects/${BRANCH_NAME}/${BUILD_NUMBER}/"
            }   
        }

    }
}