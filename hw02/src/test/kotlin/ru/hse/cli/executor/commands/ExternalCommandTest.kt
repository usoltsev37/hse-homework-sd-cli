package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.hse.cli.Environment
import ru.hse.cli.executor.IOEnvironment
import java.io.ByteArrayOutputStream

internal class ExternalCommandTest {

    @Test
    fun executeCorrect() {
        val command = ExternalCommand()

        val ioEnvironment = IOEnvironment(ByteArrayOutputStream(), ByteArrayOutputStream())

        val result = command.execute(listOf("/bin/bash", "-c", "echo hello"), ioEnvironment)
        assertEquals(0, result)
        assertEquals("hello\n", ioEnvironment.outputStream.toString())
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }

    @Test
    fun executeCorrectWithEnvironment() {
        val command = ExternalCommand()
        Environment.put("hi", "Salam lije!")

        val ioEnvironment = IOEnvironment(ByteArrayOutputStream(), ByteArrayOutputStream())

        val result = command.execute(listOf("/bin/bash", "-c", "echo \$hi"), ioEnvironment)
        assertEquals(0, result)
        assertEquals("Salam lije!\n", ioEnvironment.outputStream.toString())
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }

    @Test
    fun executeWrongWithEnvironment() {
        val command = ExternalCommand()

        val ioEnvironment = IOEnvironment(ByteArrayOutputStream(), ByteArrayOutputStream())

        val result = command.execute(listOf("/bin/bash", "-c", "eco hi"), ioEnvironment)
        assertEquals(-1, result)
        assertTrue(ioEnvironment.errorStream.toString().isNotEmpty())
    }
}
