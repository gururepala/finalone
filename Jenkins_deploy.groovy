//Declarative pipeline
pipeline{
    agent any 
    parameters {
    string(name: 'BUILD_NUMBER', defaultValue: '', description: 'From which buildnumber artifacts are doenloaded?')
  }
    stages{
        stage("Download file from S3 Bucket") {
            steps {
                    println "Downloading build code from S3"
                    sh "aws s3 ls"
                    sh "aws s3 ls s3://mydeployedprojects"
                    sh "aws s3 ls s3://mydeployedprojects/${BUILD_NUMBER}/"
            }
        }
        stage("copy file to tomcat") {
            steps {
                println "Deploying build code to tomcat server"
            }
        }
    }
}