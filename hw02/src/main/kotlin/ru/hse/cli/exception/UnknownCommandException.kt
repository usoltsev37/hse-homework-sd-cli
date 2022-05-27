package ru.hse.cli.exception

/**
 * Exception will be thrown if [ProcessBuilder] cannot start process with the particular command name
 * @param name is name of command
 */
class UnknownCommandException(name: String): Exception(name)
