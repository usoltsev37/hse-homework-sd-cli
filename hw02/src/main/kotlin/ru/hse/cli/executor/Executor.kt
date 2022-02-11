package ru.hse.cli.executor

import ru.hse.cli.executor.commands.ExternalCommand
import ru.hse.cli.parser.util.Command

/**
 * Represents Executor which execute [command] in with IO environment [ioEnvironment].
 * @param command a command for execution.
 * @param ioEnvironment stores output and error streams to print a result or error message during execution.
 */
class Executor constructor(private val command: Command, private val ioEnvironment: IOEnvironment) {

    /**
     * Execute [command] in with IO environment [ioEnvironment].
     */
    fun execute() {
        val executionCommand = CommandStorage.getCommand(command.name)

        val args = ArrayList(command.args)
        if (executionCommand is ExternalCommand) {
            args.add(0, command.name)
        }

        executionCommand.execute(args, ioEnvironment)
    }
}
