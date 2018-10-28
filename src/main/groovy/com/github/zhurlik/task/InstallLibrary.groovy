package com.github.zhurlik.task

import com.github.zhurlik.extension.ArduinoIde
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import java.nio.file.Paths

/**
 * Fetches available libraries list and install the specified one. If version is omitted, the latest is installed.
 * If a library with the same version is already installed, nothing is installed and program exits with exit code 1.
 * If a library with a different version is already installed, it’s replaced. Multiple libraries can be specified, separated by a comma.
 *
 * @author zhurlik@gmail.com
 */
class InstallLibrary extends DefaultTask {
    @Input
    String libraryName

    @TaskAction
    void action() {
        final String ideHome = project.extensions.getByType(ArduinoIde).homeDir

        project.exec {
            commandLine Paths.get(ideHome, 'arduino').toFile().path, '--install-library', libraryName
        }
    }
}
