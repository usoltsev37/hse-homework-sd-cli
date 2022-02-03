package ru.hse.cli.parser

import ru.hse.cli.parser.util.Command

interface Parser {

    /** Parse given input and build [Command]
     * @throws [IllegalStateException] when parsing fails
     * @return [Command]: representation of given string
     */
    fun parse(): Command
}
