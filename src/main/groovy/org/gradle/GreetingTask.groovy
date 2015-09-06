package org.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GreetingTask extends DefaultTask {

    String channelFile
    String outFile

    @TaskAction
    def greet() {
        def channels = project.file(channelFile).text.split().findAll({str -> str?.trim()})
        Client.startPackTask(outFile, channels);
    }
}
