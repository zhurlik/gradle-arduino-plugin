package com.github.zhurlik.task

import groovy.util.logging.Slf4j
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.apache.commons.io.FilenameUtils

/**
 * A task for extracting Arduino IDE into project directory.
 *
 * @author zhurlik
 */
@Slf4j('logger')
class Install extends DefaultTask {

    @TaskAction
    def action() {
        project.configurations['arduinoIde']
                .allDependencies
                .findAll { 'arduino'.equals(it.group) }
                .forEach { dep ->
            final File dist = project.configurations['arduinoIde']
                    .files
                    .find { it.name.contains(dep.name) }
            final String type = FilenameUtils.getExtension(dist.path)

            switch (type) {
                case 'xz': unxz(dist)
                    break
                case 'zip': unzip(dist)
                    break
                default: throw UnsupportedOperationException("Unsupported archive: ${dist}")
            }
        }
    }

    def unzip(final File dist) {
        project.copy {
            from project.zipTree(dist)
            into project.projectDir
        }
    }

    def unxz(final File dist) {
        // there is a problem with zero-byte files during extracting via ant tasks
        project.exec {
            commandLine 'tar', '-xJvf', dist.path, '-C', project.projectDir.path
        }
    }
}
