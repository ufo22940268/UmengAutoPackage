package com.bettycc

import org.gradle.api.Project
import org.gradle.api.Plugin

class UmengPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create('umeng', UmengPluginExtension)

        println project.umeng.apk
        if (!project.umeng.apk) {
            throw new Exception("Apk file path is needed!")
        }

        def file = project.file(project.umeng.apk)
        if (!(file.exists())) {
            throw new Exception("File ${file.toString()} not exists")
        }

        project.task('buildChannels') << {
            def channels = project.file(project.umeng.channel).text.split().findAll({str -> str?.trim()})
            Client.startPackTask(project.umeng.apk, channels);
        }
    }
}
