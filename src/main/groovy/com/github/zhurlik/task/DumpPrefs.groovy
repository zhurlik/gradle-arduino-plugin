package com.github.zhurlik.task

import com.github.zhurlik.extension.ArduinoIde
import com.github.zhurlik.option.Actions
import com.github.zhurlik.option.ArduinoBuilderOptions
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Paths

class DumpPrefs extends DefaultTask {
    def ArduinoBuilderOptions options = new ArduinoBuilderOptions([action: Actions.DUMP_PREFS])

    @TaskAction
    def void action() {
        final String ideHome = project.extensions.getByType(ArduinoIde).homeDir

        /**
         * -hardware /opt/arduino-1.8.7/hardware
         */
        options.hardware.add(Paths.get(ideHome, 'hardware').toString())

        /**
         * -tools /opt/arduino-1.8.7/tools-builder
         * -tools /opt/arduino-1.8.7/hardware/tools/avr
         */
        [Paths.get(ideHome, 'tools-builder'), Paths.get(ideHome, 'hardware', 'tools', 'avr')]
                .each { options.tools.add(it.toString()) }

        final List<String> args = ["-${Actions.DUMP_PREFS}",
                                   "-logger=${options.logger}"] +
                options.hardware.collect { "-hardware $it" } +
                options.tools.collect { "-tools $it" } +
                ["-fbn=${options.fqbn}"]

        /**
         * -built-in-libraries /opt/arduino-1.8.7/libraries
         * -libraries /home/zhurlik/Arduino/libraries
         * -fqbn=arduino:avr:nano:cpu=atmega328old
         * -ide-version=10807
         * -build-path /tmp/arduino_build_921969
         * -warnings=all
         * -build-cache /tmp/arduino_cache_294101
         * -prefs=build.warn_data_percentage=75
         * -prefs=runtime.tools.avrdude.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.avrdude-6.3.0-arduino14.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.arduinoOTA.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.arduinoOTA-1.2.1.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.avr-gcc.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -prefs=runtime.tools.avr-gcc-5.4.0-atmel3.6.1-arduino2.path=/opt/arduino-1.8.7/hardware/tools/avr
         * -verbose /github/smart-home/arduino/prototype/src/arduino/main/main.ino
         */

        validate(options)
        project.exec {
            commandLine "${ideHome}/arduino-builder" + args
        }
    }

    private void validate(final ArduinoBuilderOptions arduinoBuilderOptions) {
        if (Actions.DUMP_PREFS != options.action) {
            throw new IllegalArgumentException('Action should be: dump-prefs')
        }

        // -hardware: Mandatory. Folder containing Arduino platforms.
        if (options.hardware.isEmpty()) {
            throw new IllegalArgumentException('-hardware option is mandatory')
        }
        // -tools: Mandatory. Folder containing Arduino tools (gcc, avrdude...)
        if (options.tools.isEmpty()) {
            throw new IllegalArgumentException('-tools option is mandatory')
        }
        // -fqbn: Mandatory. Fully Qualified Board Name, e.g.: arduino:avr:uno
        if (options.fqbn in [null, '']) {
            throw new IllegalArgumentException('-fqbn option is mandatory')
        }
    }
}
