#!/usr/bin/groovy

def call(Map parameters = [:], body) {

    def defaultLabel = "s2iImage.${env.JOB_NAME}.${env.BUILD_NUMBER}".replace('-', '_').replace('/', '_')
    def label = parameters.get('label', defaultLabel)

    def s2iImage = parameters.get('s2iImage', 'fabric8/s2-builder:0.0.1')

    def inheritFrom = parameters.get('inheritFrom', 'base')


        podTemplate(label: label, inheritFrom: "${inheritFrom}",
                containers: [
                        [name: 's2i', image: "${s2iImage}", command: '/bin/sh -c', args: 'cat', ttyEnabled: true]],
                volumes: [
                        hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')]) {
            body()
        }


}