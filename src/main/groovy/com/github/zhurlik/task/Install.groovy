package com.github.zhurlik.task

import groovy.util.logging.Slf4j
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.org.apache.commons.io.FilenameUtils

import java.nio.file.Paths

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
            into ideHome()
        }
    }

    def unxz(final File dist) {
        final File tmp = Paths.get(project.projectDir.path, 'arduino-ide.tmp').toFile()
        project.ant.taskdef(name: 'unxz',
                classname: 'org.apache.ant.compress.taskdefs.UnXZ',
                classpath: project.configurations.antCompress.asPath)
        project.ant.unxz(src: dist, dest: tmp)
        project.copy {
            from project.tarTree(tmp)
            into ideHome()
        }
        project.delete(tmp)
    }

    File ideHome() {
        final File ideHomeDir = project.extensions.getByName("ArduinoIde").home
        return (ideHomeDir == null ? project.projectDir : ideHomeDir)
    }
}
