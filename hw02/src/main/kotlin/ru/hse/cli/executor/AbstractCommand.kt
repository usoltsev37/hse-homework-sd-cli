package ru.hse.cli.executor

/**
 * Represents a CLI command that can be executed by [execute].
 */
abstract class AbstractCommand {
    /**
     * Executes the command with arguments [args] and IO environment [ioEnvironment].
     * @param args command arguments for execution.
     * @param ioEnvironment stores output and error streams to print a result or error message during execution.
     * @return execution result code.
     */
    abstract fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int
}
