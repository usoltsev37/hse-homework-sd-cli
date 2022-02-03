package ru.hse.cli.parser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.hse.cli.parser.impl.ParserImpl
import ru.hse.cli.parser.util.Command

class ParserTest {

    @Test
    fun testOneArg() = doTest("cat test.txt", Command("cat", listOf("test.txt")))

    @Test
    fun testDoubleQuotes() = doTest("echo \"Hello, 'ee'world!\"", Command("echo", listOf("Hello, 'ee'world!")))

    @Test
    fun testSingleQuotes() = doTest("echo 'nan\"\"ana'", Command("echo", listOf("nan\"\"ana")))

    @Test
    fun testCommandWithoutArgs() = doTest("pwd", Command("pwd", emptyList()))

    @Test
    fun testSeveralArgs() = doTest("wc a.txt b.txt", Command("wc", listOf("a.txt", "b.txt")))

    @Test
    fun testCommandInQuotes() = doTest("\"echo\" 'lol'", Command("echo", listOf("lol")))

    @Test
    fun testSpaces() = doTest("echo      'fewe    f '   ", Command("echo", listOf("fewe    f ")))

    @Test
    fun testAssignment() = doTest("a = bbb ", Command("assignment", listOf("a", "bbb")))

    @Test
    fun testAssignmentWithoutSpaces() = doTest("a='b'", Command("assignment", listOf("a", "b")))

    private fun doTest(input: String, expectedCommand: Command) {
        val parser = ParserImpl(input)

        val command = parser.parse()

        Assertions.assertEquals(expectedCommand, command)
    }
}
