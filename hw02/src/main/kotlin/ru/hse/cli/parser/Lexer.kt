package ru.hse.cli.parser

import ru.hse.cli.parser.util.Token

/**
 * Interface for the SayShell lexer
 *
 * Splits the input string into tokens
 * @see [Parser]
 * @see [Token]
 */
interface Lexer {

    /**
     * Gives next token from input string
     * @return [Token]: next token
     * @throws IllegalStateException if [isExhausted] is true or lexer did not find a matching token
     */
    fun getNextToken(): Token?

    /**
     * The lexer is exhausted if it reaches the end of the line
     */
    fun isExhausted(): Boolean
}
