package ru.hse.cli.exception

/**
 * Exception will be thrown if Lexer fails to produce the next token
 * @param name exception message
 */
class LexerException(name: String): Exception(name)

