package ru.hse.cli.exception

/**
 * Exception will be thrown if Parser fails to parse current token
 * @param name exception message
 */
class ParserException(name: String): Exception(name)

