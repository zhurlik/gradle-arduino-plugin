package com.github.zhurlik.task

import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.gradle.process.internal.ExecException
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.nio.file.Paths

import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue

class DumpPrefsTest {
    private static final File PROJECT_DIR = Paths.get(InstallTest.getClassLoader().getResource('./').file, 'test-project').toFile()
    private Project testProject

    @BeforeEach
    void setUp() {
        FileUtils.deleteDirectory(PROJECT_DIR)
        testProject = ProjectBuilder.builder()
                .withName('test-project')
                .withProjectDir(PROJECT_DIR)
                .build()

        testProject.pluginManager.apply('com.github.zhurlik.arduino')
        testProject.extensions.getByName('ArduinoIde').homeDir = '/test/arduino-ide'

        testProject.tasks.create('testTask', DumpPrefs) {
            options.buildPath = 'test'
            options.fqbn = 'arduino:avr:nano:cpu=atmega328old'
            sketch = 'test.ino'
        }
    }

    @Test
    void testMain() {
        final ExecException exp = assertThrows(ExecException, {
            testProject.tasks['testTask'].actions.each {
                it.execute(testProject.tasks['testTask'])
            }
        })

        assertTrue(exp.getMessage().contains("A problem occurred starting process 'command '/test/arduino-ide/arduino-builder"))
    }
}
