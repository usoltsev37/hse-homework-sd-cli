package ru.hse.cli.executor

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.hse.cli.parser.util.Command
import java.io.ByteArrayOutputStream

internal class ExecutorTest {

    @Test
    fun executeCorrect() {
        val ioEnvironment = IOEnvironment(ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor = Executor(Command(name = "echo", listOf("hello")), ioEnvironment)

        executor.execute()

        assertEquals("hello", ioEnvironment.outputStream.toString())
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }

    @Test
    fun executeNotCorrect() {
        val fileName = "somefile.txt"
        val ioEnvironment = IOEnvironment(ByteArrayOutputStream(), ByteArrayOutputStream())
        val executor = Executor(Command(name = "cat", listOf(fileName)), ioEnvironment)

        executor.execute()

        assertEquals("File '$fileName' does not exist!", ioEnvironment.errorStream.toString())
    }
}
