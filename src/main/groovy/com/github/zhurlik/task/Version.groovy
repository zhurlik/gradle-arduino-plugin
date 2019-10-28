package com.github.zhurlik.task

import com.github.zhurlik.extension.ArduinoIde
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Paths

/**
 * Print the version information and exit.
 *
 * @author zhurlik@gmail.com
 */
class Version  extends DefaultTask {
    @TaskAction
    void action() {
        final String ideHome = project.extensions.getByType(ArduinoIde).homeDir

        project.exec {
            commandLine Paths.get(ideHome, 'arduino').toFile().path, '--version'
        }
    }
}
