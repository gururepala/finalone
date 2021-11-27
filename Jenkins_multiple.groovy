//declarative pipeline
pipeline {
    agent any 
     parameters {
    string(name: 'SERVERIPS', defaultValue: '', description: 'Enter the serverips in which code has to be build?')
  }
    stages{
        stage("multiple servers") {
            steps{
                    sh '''
                    IFS=',' read -ra AADR <<< "${SERVERIPS}"
                    for ip in \"${ADDR[@]}\";
                    do
                    echo $ip
                    echo "here we copy files"   
                    ssh -o stricthostkeychecking=no -i /tmp/DevOpsNV.pem ec2-user@$ip "hostname"
                    #process "$ip"
                    done
                    ''' 
            }
        }
    }
}