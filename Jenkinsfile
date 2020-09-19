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
            sh "docker build -t hothoony/k8s-test:${TAG} ."
            sh "docker tag hothoony/k8s-test:${TAG} hothoony/k8s-test:latest"
        }
        stage("push") {
            sh "docker login -u ${DOCKER_UID} -p ${DOCKER_PWD}"
            sh "docker push hothoony/k8s-test:${TAG}"
            sh "docker push hothoony/k8s-test:latest"
            sh "docker rmi hothoony/k8s-test:${TAG}"
            sh "docker rmi hothoony/k8s-test:latest"
        }
        stage("deploy") {
            def K8S_CLUSTER = "hothoony@192.168.219.86"
            sh "ssh ${K8S_CLUSTER} rm -rf ~/deploy/"
            sh "scp -r deploy/ ${K8S_CLUSTER}:~"
            sh "ssh ${K8S_CLUSTER} sed -i 's/{DOCKER_TAG}/${TAG}/g' deploy/pod-example.yaml"
            sh "ssh ${K8S_CLUSTER} kubectl apply -f deploy/pod-example.yaml"
        }
    }
}

def getDockerTag() {
//     def tag = sh script: "git rev-parse HEAD", returnStdout: true
//     def tag = sh script: "git log -n 1 --pretty=%H", returnStdout: true
    def tag = sh script: "git log -n 1 --pretty=%h", returnStdout: true
    return tag
}
