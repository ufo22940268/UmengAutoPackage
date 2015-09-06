package com.bettycc

import org.gradle.api.Project
import org.gradle.api.Plugin

class UmengPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.task('hello', type: UmengTask)
    }
}
