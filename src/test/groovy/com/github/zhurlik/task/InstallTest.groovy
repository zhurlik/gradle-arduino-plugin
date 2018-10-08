package com.github.zhurlik.task

import groovy.util.logging.Slf4j
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.nio.file.Paths

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

@Slf4j('logger')
class InstallTest {
    private static final File PROJECT_DIR = Paths.get(InstallTest.getClassLoader().getResource('./').file, 'test-project').toFile()

    // for getting fake distributes
    private static final String FAKE_REPO = InstallTest.getClassLoader().getResource('fake-dist').file

    private Project testProject

    @BeforeEach
    void setUp() {
        FileUtils.deleteDirectory(PROJECT_DIR)
        testProject = ProjectBuilder.builder()
                .withName('test-project')
                .withProjectDir(PROJECT_DIR)
                .build()

        // NOTE: for testing that's important
        testProject.repositories {
            flatDir {
                dirs FAKE_REPO
            }
        }

        testProject.pluginManager.apply('com.github.zhurlik.arduino')
    }

    @Test
    void testLinux64() {
        testProject.dependencies {
            arduinoIde 'arduino:linux64:1.8.7@tar.xz'
        }

        testProject.tasks['install'].actions.each {
            it.execute(testProject.tasks.install)
        }

        final File ideDir = Paths.get(testProject.projectDir.path, 'arduino-1.8.7').toFile()
        assertTrue(ideDir.exists())
        assertTrue(ideDir.isDirectory())
        assertEquals(4, ideDir.listFiles()
                .collect { it.name }
                .findAll { it in ['arduino', 'arduino-builder', 'tools-builder', 'hardware'] }
                .size())
    }

    @Test
    void testLinux32() {
        testProject.dependencies {
            arduinoIde 'arduino:linux32:1.8.7@tar.xz'
        }

        testProject.tasks['install'].actions.each {
            it.execute(testProject.tasks.install)
        }

        final File ideDir = Paths.get(testProject.projectDir.path, 'arduino-1.8.7').toFile()
        assertTrue(ideDir.exists())
        assertTrue(ideDir.isDirectory())
        assertEquals(4, ideDir.listFiles()
                .collect { it.name }
                .findAll { it in ['arduino', 'arduino-builder', 'tools-builder', 'hardware'] }
                .size())
    }

    @Test
    void testArm() {
        testProject.dependencies {
            arduinoIde 'arduino:r1-linuxarm:1.8.7@tar.xz'
        }

        testProject.tasks['install'].actions.each {
            it.execute(testProject.tasks.install)
        }

        final File ideDir = Paths.get(testProject.projectDir.path, 'arduino-1.8.7').toFile()
        assertTrue(ideDir.exists())
        assertTrue(ideDir.isDirectory())
        assertEquals(4, ideDir.listFiles()
                .collect { it.name }
                .findAll { it in ['arduino', 'arduino-builder', 'tools-builder', 'hardware'] }
                .size())
    }

    @Test
    void testMacOSX() {
        testProject.dependencies {
            arduinoIde 'arduino:macosx:1.8.7@zip'
        }

        testProject.tasks['install'].actions.each {
            it.execute(testProject.tasks.install)
        }

        final File ideDir = Paths.get(testProject.projectDir.path, 'Arduino.app').toFile()
        assertTrue(ideDir.exists())
        assertTrue(ideDir.isDirectory())
        assertEquals('[Contents]', ideDir.listFiles().collect { it.name }.toString())
    }

    @Test
    void testWindows() {
        testProject.dependencies {
            arduinoIde 'arduino:windows:1.8.7@zip'
        }

        testProject.tasks['install'].actions.each {
            it.execute(testProject.tasks.install)
        }

        final File ideDir = Paths.get(testProject.projectDir.path, 'arduino-1.8.7').toFile()
        assertTrue(ideDir.exists())
        assertTrue(ideDir.isDirectory())
        assertEquals(4, ideDir.listFiles()
                .collect { it.name }
                .findAll { it in ['arduino.exe', 'arduino-builder.exe', 'tools-builder', 'hardware'] }
                .size())
    }
}
