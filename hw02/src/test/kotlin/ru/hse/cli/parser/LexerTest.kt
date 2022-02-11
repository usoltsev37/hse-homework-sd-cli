package ru.hse.cli.parser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.hse.cli.parser.impl.LexerImpl
import ru.hse.cli.parser.util.Token

class LexerTest {

    @Test
    fun testDoubleQuotes() = doTest("echo \"Hello, 'ee'world!\"", Token.TK_STR, Token.TK_SPACE, Token.TK_STR_IN1)

    @Test
    fun testSingleQuotes() = doTest("echo 'Hello, \"ee\"world!'", Token.TK_STR, Token.TK_SPACE, Token.TK_STR_IN2)

    @Test
    fun testMultipleArgs() = doTest("wc a.txt b.txt", Token.TK_STR, Token.TK_SPACE, Token.TK_STR, Token.TK_SPACE, Token.TK_STR)

    @Test
    fun testLongSpaces() = doTest("wc       ' w  w'   ", Token.TK_STR, Token.TK_SPACE, Token.TK_STR_IN2, Token.TK_SPACE)

    @Test
    fun testAssignment() = doTest("a=b", Token.TK_STR, Token.TK_ASSIGN, Token.TK_STR)

    @Test
    fun testLexPipe() = doTest("echo ww | wc", Token.TK_STR, Token.TK_SPACE, Token.TK_STR, Token.TK_SPACE,
                                                     Token.TK_PIPE, Token.TK_SPACE, Token.TK_STR)

    private fun doTest(input: String, vararg expectedTokens: Token) {
        val lexer = LexerImpl(input)
        val tokens = ArrayList<Token>()
        while (!lexer.isExhausted()) {
            tokens.add(lexer.getNextToken())
        }
        Assertions.assertEquals(expectedTokens.asList(), tokens)
    }
}
