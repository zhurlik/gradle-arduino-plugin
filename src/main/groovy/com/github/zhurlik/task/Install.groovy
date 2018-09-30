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
        def dist = project.configurations['arduinoIde'].files[0]
        def to = Paths.get(project.projectDir.path, 'arduino-ide').toFile()

        def type = FilenameUtils.getExtension(dist.path)

        // TODO: other types and OS
        switch (type) {
            case 'xz': unxz(dist, to)
                break
            default: throw UnsupportedOperationException("Unsupported archive: ${dist}")
        }
    }

    private void unxz(File dist, to) {
        project.ant.taskdef(name: 'unxz',
                classname: 'org.apache.ant.compress.taskdefs.UnXZ',
                classpath: project.configurations.antCompress.asPath)
        project.ant.unxz(src: dist, dest: to)
        project.copy {
            from project.tarTree(to)
            into project.projectDir
        }
        project.delete(to)
    }
}
