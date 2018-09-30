package com.github.zhurlik.task

import groovy.util.logging.Slf4j
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.nio.file.Paths

@Slf4j('logger')
class InstallTest {
    private static final File PROJECT_DIR = Paths.get(InstallTest.getClassLoader().getResource('./').file, 'test-project').toFile()

    @BeforeEach
    void setUp() {
        FileUtils.deleteDirectory(PROJECT_DIR)
    }

    @Test
    void testMain() {
        final Project testProject = ProjectBuilder.builder()
                .withName('test-project')
                .withProjectDir(PROJECT_DIR)
                .build()
        testProject.pluginManager.apply('com.github.zhurlik.arduino')

        testProject.dependencies {
            arduinoIde 'arduino:linux64:1.8.7@tar.xz'
        }

        testProject.tasks['install'].actions.each {
            it.execute(testProject.tasks.install)
        }

        final File ideDir = Paths.get(testProject.projectDir.path, 'arduino-1.8.7').toFile()
        assertTrue(ideDir.exists())
        assertTrue(ideDir.isDirectory())
    }
}
