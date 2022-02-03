package ru.hse.cli.executor.commands

import ru.hse.cli.executor.IOEnvironment

/**
 * Represents a CLI command that can be executed by [execute].
 */
interface AbstractCommand {
    /**
     * Executes the command with arguments [args] and IO environment [ioEnvironment].
     * @param args command arguments for execution.
     * @param ioEnvironment stores output and error streams to print a result or error message during execution.
     * @return execution result code.
     */
    fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int
}
