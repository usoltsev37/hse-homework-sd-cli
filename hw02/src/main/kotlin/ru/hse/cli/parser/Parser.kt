package ru.hse.cli.parser

import ru.hse.cli.parser.util.Command

/**
 * Interface for the SayShell parser
 * @see [Lexer]
 */
interface Parser {

    /** Parse given input and build [Command]
     * @throws [IllegalStateException] when parsing fails
     * @return [Command]: representation of given string
     */
    fun parse(): List<Command>
}
