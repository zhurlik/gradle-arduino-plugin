package com.github.zhurlik.task

import groovy.util.logging.Slf4j
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.apache.commons.io.FilenameUtils

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
            into project.projectDir
        }
    }

    def unxz(final File dist) {
        final File tmp = Paths.get(getTemporaryDir().path, 'downloaded-tmp.tar.xz').toFile()

        // ant task
        project.ant.taskdef(name: 'unxz',
                classname: 'org.apache.ant.compress.taskdefs.UnXZ',
                classpath: project.configurations.antCompress.asPath)
        project.ant.unxz(src: dist, dest: tmp)

        // untar
        project.copy {
            from project.tarTree(tmp)
            into project.projectDir
        }
        // del temp file
        project.delete(tmp)
    }
}
