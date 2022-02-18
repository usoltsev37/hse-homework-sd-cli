package ru.hse.cli.parser.impl

import ru.hse.cli.exception.LexerException
import ru.hse.cli.parser.Lexer
import ru.hse.cli.parser.util.Token

/**
 * Base implementation of [Lexer].
 * @see [Token]
 */
class LexerImpl(private val input: String) : Lexer {

    /**
     * Current position in input string
     */
    var pos: Int = 0

    private val myIsExhausted: Boolean
        get() = pos >= input.length

    override fun getNextToken(): Token {
        check(!isExhausted())

        for (tok in Token.values()) {
            val endOfToken = tok.endOfMatch(input.substring(pos))
            if (endOfToken != -1) {
                pos += endOfToken
                return tok
            }
        }

        throw LexerException(
            """
            Lexer Error!
            Command: $input
            Position $pos
            """.trimIndent()
        )
    }

    override fun isExhausted(): Boolean {
        return myIsExhausted
    }

    /**
     * @return input substring from start to pos
     */
    fun getInputSubstr(start: Int): String {
        return input.substring(start, pos)
    }
}
