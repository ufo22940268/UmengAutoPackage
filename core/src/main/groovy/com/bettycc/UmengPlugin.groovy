package com.bettycc

import org.gradle.api.Project
import org.gradle.api.Plugin

class UmengPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create('umeng', UmengPluginExtension)
        target.task('hello') << {
            println "apk: ${project.umeng.apk}"
        }
    }
}
