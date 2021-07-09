job('NodeJS Docker example') {
    scm {
        git('https://github.com/ewalasagas/docker-demo.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('ewalasagas/docker-nodejs-demo')     //docker image on dockerhub repo
            tag('${GIT_REVISION,length=9}') //unique sha cmd - for every git cmd you get a unique string (this is the version control)
            registryCredentials('dockerhub')
            //all foudn here https://jenkinsci.github.io/job-dsl-plugin/#method/javaposse.jobdsl.dsl.helpers.step.MultiJobStepContext.dockerBuildAndPublish
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}