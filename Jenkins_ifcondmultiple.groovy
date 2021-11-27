 pipeline {
    agent any
     parameters{
        //string(name: ENVIRONMENT_NAME , defaultValue:QA,description:"")
        string(name: 'ENVIRONMENT_NAME', defaultValue: '', description: '')
    }

    // ----------------

    stages {
        stage('Build Container') {
            steps {
                echo 'Building Container..'

                script {
                    if (env.ENVIRONMENT_NAME == 'GURU') {
                        SERVERS = ["172.31.80.160","172.31.86.77"]
                    } else if (env.ENVIRONMENT_NAME == 'PRASAD') {
                        SERVERS = ["172.31.80.160"]
                    }
                    println SERVERS.size()
                    for(item in SERVERS){
                     println item
                     sh 'echo $item'
                     sh """
                        echo "download warfirle from s3"
                        echo "scp file to $item"
                        ssh -o stricthostkeychecking=no -i /tmp/DevOpsNV.pem ec2-user@$item "hostname"
                        """
                        }
                   // echo SERVERS
                }
                // echo 'Building Branch: ' + env.BRANCH_NAME
                // echo 'Build Number: ' + env.BUILD_NUMBER
                // echo 'Building Environment: ' + ENV_NAME

                // echo "Running your service with environemnt ${ENV_NAME} now"
            }
        }
    }
}