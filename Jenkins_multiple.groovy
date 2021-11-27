//declarative pipeline
pipeline {
    agent any 
     parameters {
    string(name: 'SERVERIPS', defaultValue: '', description: 'Enter the serverips in which code has to be build?')
    string(name: 'BRANCH', defaultValue: '', description: 'Enter the BRANCH from which SRC is taken?')
    string(name: 'BUILD', defaultValue: '', description: 'Enter the BUILD number!!')
  }
    stages{
        stage("multiple servers") {
            steps {
                    sh '''
                        aws s3 cp s3://mydeployedprojects/${BRANCH}/${BUILD}/hello-${BUILD}.war .
                        ls -l
                        IFS=',' read -ra ADDR <<< "${SERVERIPS}"
                        for ip in \"${ADDR[@]}\";
                        do
                        echo $ip
                        scp -o stricthostkeychecking=no -i /tmp/DevOpsNV.pem hello-${BUILD}.war ec2-user@$ip:/var/lib/tomcat/webapps
                        ssh -o stricthostkeychecking=no -i /tmp/DevOpsNV.pem ec2-user@$ip "hostname"
                        #process "$ip"
                        done
                           '''
            }
        }
    }
}