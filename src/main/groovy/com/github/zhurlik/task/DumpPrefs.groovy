package com.github.zhurlik.task

import com.github.zhurlik.extension.ArduinoIde
import com.github.zhurlik.option.Actions
import com.github.zhurlik.option.ArduinoBuilderOptions
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DumpPrefs extends DefaultTask {
    ArduinoBuilderOptions options = new ArduinoBuilderOptions([action: Actions.DUMP_PREFS])
    // sometimes there is a need to modify options manually
    Closure optinsCustomizer = {}
    String sketch

    @TaskAction
    void action() {
        final String ideHome = project.extensions.getByType(ArduinoIde).homeDir

        options.addHardware(ideHome)
        options.addTools(ideHome)
        options.addLibraries()
        options.addPrefs(ideHome)
        options.validate(Actions.DUMP_PREFS)
        options.customize(optinsCustomizer)

        project.exec {
            commandLine "${ideHome}/arduino-builder" + options.convertToArgs() + sketch
        }
    }
}
