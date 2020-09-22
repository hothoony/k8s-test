properties([pipelineTriggers([githubPush()])])

node {
    git url: "https://github.com/hothoony/k8s-test", branch: "master"

    def TAG = "${BUILD_NUMBER}"
//     def TAG = getDockerTag()

    withCredentials([[$class: "UsernamePasswordMultiBinding",
        credentialsId: "dockerhub",
        usernameVariable: "DOCKER_UID",
        passwordVariable: "DOCKER_PWD"]]) {

        stage("pull") {
            git "https://github.com/hothoony/k8s-test"
        }
        stage("test") {
            sh "./gradlew test"
        }
        stage("build gradle") {
            sh "./gradlew clean build"
        }
        stage("build docker") {
            slackSend channel: '#jenkins', color: '#FFFF00', message: "STARTED: build docker"
            sh "docker build -t hothoony/k8s-test:${TAG} ."
            sh "docker tag hothoony/k8s-test:${TAG} hothoony/k8s-test:latest"
        }
        stage("push dockerhub") {
            slackSend channel: '#jenkins', color: '#FFFF00', message: "STARTED: push dockerhub"
            sh "docker login -u ${DOCKER_UID} -p ${DOCKER_PWD}"
            sh "docker push hothoony/k8s-test:${TAG}"
            sh "docker push hothoony/k8s-test:latest"
            sh "docker rmi hothoony/k8s-test:${TAG}"
            sh "docker rmi hothoony/k8s-test:latest"
        }
        stage("deploy k8s") {
            slackSend channel: '#jenkins', color: '#FFFF00', message: "STARTED: deploy k8s"
            def K8S_CLUSTER = "hothoony@192.168.219.81"
            sh "ssh ${K8S_CLUSTER} rm -rf deploy/"
            sh "scp -r deploy/ ${K8S_CLUSTER}:~"
            sh "ssh ${K8S_CLUSTER} sed -i 's/{TAG}/${TAG}/g' deploy/deployment.yaml"
            sh "ssh ${K8S_CLUSTER} kubectl apply -f deploy/deployment.yaml"
        }
    }
}

def getDockerTag() {
//     def tag = sh script: "git rev-parse HEAD", returnStdout: true
//     def tag = sh script: "git log -n 1 --pretty=%H", returnStdout: true
    def tag = sh script: "git log -n 1 --pretty=%h", returnStdout: true
    return tag
}
