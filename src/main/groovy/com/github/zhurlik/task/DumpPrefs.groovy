package com.github.zhurlik.task

import com.github.zhurlik.extension.ArduinoIde
import com.github.zhurlik.option.ArduinoBuilderOptions
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DumpPrefs extends DefaultTask {
    def ArduinoBuilderOptions options

    @TaskAction
    def void action() {
        final String ideHome = project.extensions.getByType(ArduinoIde).homeDir
        project.exec {
            commandLine "${ideHome}/arduino-builder", '--version'
        }
    }
}
