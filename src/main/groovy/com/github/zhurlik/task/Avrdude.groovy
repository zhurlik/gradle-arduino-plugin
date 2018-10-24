package com.github.zhurlik.task

import com.github.zhurlik.extension.ArduinoIde
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * A gradle task for using avrdude that was installed within Arduino Studio.
 *
 * To be able to execute something like this:
 * arduino-1.8.7/hardware/tools/avr/bin/avrdude -C/arduino-1.8.7/hardware/tools/avr/etc/avrdude.conf -c avrispmkII -p m328p -U flash:w:"build/bootloaders-hex/192-168-100-201.hex":i -B10
 *
 * @author zhurlik@gmail.com
 */
class Avrdude extends DefaultTask {
    // todo: make a class
    List<String> options = new ArrayList<>()

    @TaskAction
    void action() {
        final String ideHome = project.extensions.getByType(ArduinoIde).homeDir

        final List<String> args = new LinkedList<>()
        // avrdude
        args.add("${ideHome}/hardware/tools/avr/bin/avrdude")

        // required
        args.add("-C${ideHome}/hardware/tools/avr/etc/avrdude.conf")

        args.addAll(options)

        project.exec {
            commandLine args
        }
    }
}
