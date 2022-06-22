package ru.hse.cli.executor

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.hse.cli.parser.util.Command
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets

internal class ExecutorTest: BaseExecutorTest() {

    @Test
    fun executeCorrect() {
        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(0)), ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor = Executor(listOf(Command(name = "echo", listOf("hello"))), ioEnvironment)

        executor.execute()

        assertEquals("hello", ioEnvironment.outputStream.toString())
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }

    @Test
    fun executeNotCorrect() {
        val fileName = "somefile.txt"
        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(0)), ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor = Executor(listOf(Command(name = "cat", listOf(fileName))), ioEnvironment)

        executor.execute()

        assertEquals("File '$fileName' does not exist!", ioEnvironment.errorStream.toString())
    }

    @Test
    fun executeWithPipeAndExternalCommand() {
        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(0)), ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor =
            Executor(listOf(Command(name = "pwd"), Command(name = "git", listOf("--version"))), ioEnvironment)

        executor.execute()

        assertTrue(ioEnvironment.outputStream.toString().startsWith("git version"))
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }

    @Test
    fun executeWithPipeAndWc() {
        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(0)), ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor = Executor(listOf(Command(name = "echo", listOf("first")), Command(name = "wc")), ioEnvironment)

        executor.execute()

        assertEquals("1 1 5", ioEnvironment.outputStream.toString())
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }

    @Test
    fun executePipeAndExit() {
        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(0)), ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor = Executor(listOf(Command(name = "echo", listOf("first")), Command(name = "exit"), Command(name = "echo", listOf("second"))), ioEnvironment)

        executor.execute()

        assertEquals("first", IOUtils.toString(ioEnvironment.inputStream, StandardCharsets.UTF_8))
        assertTrue(ioEnvironment.outputStream.toString().isEmpty())
    }

    @Test
    fun executePipeAssigment() {
        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(0)), ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor = Executor(listOf(Command("echo", listOf("a")),
            Command("assignment", listOf("b", "a")), Command("wc")), ioEnvironment)

        executor.execute()

        assertEquals("0 0 0", ioEnvironment.outputStream.toString())
    }

    @Test
    fun executeEchoPipeCat() {
        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(0)), ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor = Executor(listOf(Command("echo", listOf("123")),
            Command("cat", emptyList())), ioEnvironment)

        executor.execute()

        assertEquals("123", ioEnvironment.outputStream.toString())
    }
}
