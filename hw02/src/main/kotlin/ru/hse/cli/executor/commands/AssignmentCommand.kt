package ru.hse.cli.executor.commands

import ru.hse.cli.Environment
import ru.hse.cli.executor.IOEnvironment

class AssignmentCommand: AbstractCommand {
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        Environment.vars[args[0]] = args[1]
        return 0
    }
}
