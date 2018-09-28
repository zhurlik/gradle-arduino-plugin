package com.github.zhurlik

import groovy.util.logging.Slf4j
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Slf4j
class ArduinoPluginTest {

    @Test
    void testApplyPlugin() {
        final Project testProject = ProjectBuilder.builder()
                .withName('test-project')
                .build()

        testProject.getPluginManager()
                .apply('com.github.zhurlik.arduino')

        Assertions.assertEquals(1, testProject.getConfigurations ().getByName('arduinoIde').files.size())
    }
}