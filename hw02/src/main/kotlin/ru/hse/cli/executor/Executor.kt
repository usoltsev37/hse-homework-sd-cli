package ru.hse.cli.executor

import ru.hse.cli.Environment
import ru.hse.cli.executor.commands.ExternalCommand
import ru.hse.cli.parser.util.Command
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Represents Executor which execute [commands] in with IO environment [ioEnvironment].
 * @param commands a command for execution.
 * @param ioEnvironment stores output and error streams to print a result or error message during execution.
 */
class Executor constructor(private val commands: List<Command>, private val ioEnvironment: IOEnvironment) {
    /**
     * Execute [commands] in with IO environment [ioEnvironment].
     */
    fun execute() {
        for (command in commands) {
            ioEnvironment.outputStream = ByteArrayOutputStream()
            val executionCommand = CommandStorage.getCommand(command.name)

            val args = ArrayList(command.args)
            if (executionCommand is ExternalCommand) {
                args.add(0, command.name)
            }

            val exitCode = executionCommand.execute(args, ioEnvironment)

            if (Environment.isShutdowned) {
                break
            }

            if (exitCode != 0) {
                println("Error during ${command.name} execution")
                break
            }

            ioEnvironment.inputStream = ByteArrayInputStream(ioEnvironment.outputStream.toString().toByteArray())
        }
    }
}
