//declarative pipeline
pipeline {
    agent any 
     parameters {
    string(name: 'SERVERIPS', defaultValue: '', description: 'Enter the serverip's in which code has to be build?')
  }
    stages{
        stage("multiple servers") {
            step{
                    sh """
                    IFS=',' read -ra AADR <<< "${SERVERIPS}"
                    for ip in "${ADDR[@]}";
                    do 
                    echo $ip
                    #process "$ip"
                    done
                    """
            }
        }
    }
}