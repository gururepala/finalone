//Declarative pipeline
pipeline{
    agent any 
    parameters {
    string(name: 'BRANCH_NAME', defaultValue: '${BARNCH}', description: 'From which branch artifacts are to be downloaded?')
    string(name: 'BUILD_NUMBER', defaultValue: '', description: 'From which buildnumber artifacts are downloaded?')
    string(name: 'SERVERIP', defaultValue: '', description: 'To which server builded code shall be deployed?')
  }
    stages{
        stage("Download file from S3 Bucket") {
            steps {
                    println "Downloading build code from S3"
                    sh "aws s3 ls"
                    sh "aws s3 ls s3://mydeployedprojects"
                    sh "aws s3 ls s3://mydeployedprojects/${BRANCH_NAME}/${BUILD_NUMBER}/"
                    sh "aws s3 cp s3://mydeployedprojects/${BRANCH_NAME}/${BUILD_NUMBER}/hello-${BUILD_NUMBER}.war ."
            }
        }
        stage("copy file to tomcat server") {
            steps {
                println "Deploying build code to tomcat server"
                sh "ssh -i /tmp/DevOpsNV.pem ec2-user@${SERVERIP} \"systemctl status tomcat\""
            }
        }
    }
}