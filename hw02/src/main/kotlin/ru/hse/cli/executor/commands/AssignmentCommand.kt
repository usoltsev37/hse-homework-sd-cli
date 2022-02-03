package ru.hse.cli.executor.commands

import ru.hse.cli.Environment
import ru.hse.cli.executor.IOEnvironment

/**
 * This [AbstractCommand] implementation introduce a variable definition in the shell environment
 */
class AssignmentCommand: AbstractCommand {

    /**
     * Puts a new variable with its value into the environment
     */
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        Environment.vars[args[0]] = args[1]
        return 0
    }
}
