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
        options.addBuiltInLibraries(ideHome)
        options.addLibraries()
        options.addPrefs(ideHome)
        options.customize(optinsCustomizer)
        options.validate(Actions.DUMP_PREFS)

        final List<String> args = new LinkedList<>()
        args.add("${ideHome}/arduino-builder")
        args.addAll(options.convertToArgs())
        args.add(sketch)

        project.exec {
            commandLine args
        }
    }
}
