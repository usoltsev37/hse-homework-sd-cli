package ru.hse.cli.parser.impl

import ru.hse.cli.parser.util.Command
import ru.hse.cli.parser.Parser
import ru.hse.cli.parser.util.Token
import java.lang.IllegalStateException

/**
 * Base implementation of [Parser].
 * Simple recursive descent parser that builds [Command] representation during parsing
 * @see [Command]
 */
class ParserImpl(input: String): Parser {

    private val lexer: LexerImpl = LexerImpl(input)

    private var curToken: Token? = lexer.getNextToken()

    private var lastPos: Int = 0

    private val errorMessage
       get() = "Parser Error: position ${lexer.pos}"

    override fun parse(): Command {
        return parseCommand()
    }

    private fun parseCommand(): Command {
        val name = parseString()
        if (lexer.isExhausted()) {
            return Command(name)
        }
        check(accept(Token.TK_SPACE)) { errorMessage }
        val args = parseArgs()
        return Command(name, args)
    }

    private fun parseArgs(): List<String> {
        val result = ArrayList<String>()
        result.add(parseString())
        while (!lexer.isExhausted() && accept(Token.TK_SPACE)) {
            result.add(parseString())
        }
        return result
    }

    private fun parseString(): String {
        val value = getLastTokenString()
        if (accept(Token.TK_STR_IN1) || accept(Token.TK_STR_IN2)) {
            return value.substring(1 until value.length - 1)
        }
        if (accept(Token.TK_STR)) {
            return value
        }
        throw IllegalStateException(errorMessage)
    }

    private fun accept(tok: Token): Boolean {
        if (curToken == tok) {
            if (!lexer.isExhausted()) {
                lastPos = lexer.pos
                curToken = lexer.getNextToken()
            }
            return true
        }
        return false
    }

    private fun getLastTokenString(): String {
        return lexer.getInputSubstr(lastPos)
    }

}
