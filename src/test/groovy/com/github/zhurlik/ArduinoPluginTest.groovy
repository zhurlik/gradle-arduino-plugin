package com.github.zhurlik

import groovy.util.logging.Slf4j
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

@Slf4j
class ArduinoPluginTest {

    @Test
    void testApplyPlugin() {
        final Project testProject = ProjectBuilder.builder()
                .withName('test-project')
                .build()

        testProject.getPluginManager()
                .apply('com.github.zhurlik.arduino')

        assertEquals(new URI('https://downloads.arduino.cc'), testProject.repositories.getByName('ivy').url)
        assertNotNull(testProject.getConfigurations ().getByName('arduinoIde'))
        assertNotNull(testProject.tasks.getByName('install'))
    }
}