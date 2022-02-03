package ru.hse.cli.executor

import ru.hse.cli.parser.util.Command

class Executor constructor(private val command: Command, private val ioEnvironment: IOEnvironment) {

    fun execute() {
        val executionCommand = CommandStorage.getCommand(command.args[0])
        val args = command.args.subList(fromIndex = 1, toIndex = command.args.size)

        executionCommand.execute(args, ioEnvironment)
    }
}
