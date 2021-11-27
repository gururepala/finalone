//Declarativepipeline
pipeline {
    agent any
    if(${ENV} == QA {
	SERVERS = ["1.1.1.1","1.1.1.2"]
    } else if (${ENV} == STAGE) {
	SERVERS = ["1.1.2.1","1.1.2.2"]
    } else if (${ENV} == PREF) {
	SERVERS = ["1.2.1.1","1.2.1.2"]
    } else {
    println "You Dindt provide any environment"
    }
    parameters {
    string(name: 'ENV', defaultValue: '', description: 'Enter the serverips in which code has to be build?')
    }
    stages{
        stage("array") {
            steps {
                println "This is empty line"
            }
        }
    }
}