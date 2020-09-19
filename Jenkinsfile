properties([pipelineTriggers([githubPush()])])

node {
    git url: 'https://github.com/hothoony/k8s-test', branch: 'master'

    def buildNumber = ${BUILD_NUMBER}
    def tag = getDockerTag()

    withCredentials([[$class: 'UsernamePasswordMultiBinding',
        credentialsId: 'dockerhub',
        usernameVariable: 'DOCKER_UID',
        passwordVariable: 'DOCKER_PWD']]) {


//         stage('pull') {
//             git 'https://github.com/hothoony/k8s-test'
//         }
        stage('test') {
            echo "buildNumber : ${buildNumber}"
            echo "BUILD_NUMBER : ${BUILD_NUMBER}"
            echo "tag: ${tag}"
        }
//         stage('build gradle') {
//             sh(script: './gradlew clean build')
//         }
//         stage('build docker') {
//             sh(script: 'docker build -t hothoony/k8s-test:v1 .')
//         }
//         stage('tag') {
//             sh(script: 'docker tag hothoony/k8s-test:v1 hothoony/k8s-test:v3')
//         }
//         stage('push') {
//             sh(script: 'docker login -u ${DOCKER_UID} -p ${DOCKER_PWD}')
//             sh(script: 'docker push hothoony/k8s-test:v3')
//         }
//         stage('deploy') {
// //             sh('docker login -u ${DOCKER_UID} -p ${DOCKER_PWD}')
// //             sh('ssh hothoony@192.168.219.86 kubectl run k8s-test --image=hothoony/k8s-test:v3')
//             sh('scp pod-example.yaml hothoony@192.168.219.86:~')
//             sh('ssh hothoony@192.168.219.86 kubectl apply -f pod-example.yaml')
//         }
    }
}

def getDockerTag() {
    def tag = sh script: 'git rev-parse HEAD', returnStdout: true
    return tag
}
