package com.bettycc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class UmengTask extends DefaultTask {

    String channelFile
    String outFile

    @TaskAction
    def greet() {
        check("channel", channelFile)
        check("apk", outFile)
        def channels = project.file(channelFile).text.split().findAll({str -> str?.trim()})
        Client.startPackTask(outFile, channels);
    }

    def check(name, file) {
        if (!file) {
            throw new Exception("${name} can't be null")
        }

        if (!project.file(file).exists()) {
            throw new Exception("${file} not exists")
        }
    }
}
