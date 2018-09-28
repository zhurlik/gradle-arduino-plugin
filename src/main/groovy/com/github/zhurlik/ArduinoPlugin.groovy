package com.github.zhurlik

import groovy.util.logging.Slf4j
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A gradle plugin for using Arduio IDE.
 *
 * @author zhurlik@gmail.com
 */
@Slf4j('logger')
class ArduinoPlugin implements Plugin<Project> {

    void apply(final Project project) {
        logger.info '>> Plugin: Arduino'

        project.repositories {
                //https://downloads.arduino.cc/arduino-1.8.7-linux64.tar.xz
                ivy {
                    url "https://downloads.arduino.cc"
                    layout 'pattern', {

                        //compile 'arduino:linux64:1.8.7@tar.xz'
                        //This maps to the pattern: [organisation]:[module]:[revision]:[classifier]@[ext]

                        artifact '/[organisation]-[revision]-[module].[ext]'
                    }
                }
        }

        project.configurations {
            arduinoStudio
        }

        project.dependencies {
            arduinoStudio 'arduino:linux64:1.8.7@tar.xz'
        }
    }
}