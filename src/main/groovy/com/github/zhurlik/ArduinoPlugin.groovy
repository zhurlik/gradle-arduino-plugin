package com.github.zhurlik

import com.github.zhurlik.extension.ArduinoIde
import com.github.zhurlik.task.Avrdude
import com.github.zhurlik.task.Compile
import com.github.zhurlik.task.DumpPrefs
import com.github.zhurlik.task.Install
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

        project.extensions.add("ArduinoIde", ArduinoIde)

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
            mavenCentral()
        }

        project.configurations {
            arduinoIde
            antCompress
        }

        project.dependencies {
            antCompress group: 'org.apache.ant', name: 'ant-compress', version: '1.5'
            antCompress group: 'commons-io', name: 'commons-io', version: '2.6'
        }

        project.tasks.create('install', Install)
        project.tasks.create('dump-prefs', DumpPrefs)
        project.tasks.create('compile', Compile)
        project.tasks.create('avrdude', Avrdude)
    }
}