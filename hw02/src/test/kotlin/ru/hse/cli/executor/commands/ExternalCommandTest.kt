package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.hse.cli.Environment
import ru.hse.cli.executor.IOEnvironment
import java.io.ByteArrayOutputStream
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import java.io.ByteArrayInputStream


internal class ExternalCommandTest {

    @Test
    fun executeCorrect() {
        val command = ExternalCommand()

        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(1)), ByteArrayOutputStream(), ByteArrayOutputStream())

        val result = command.execute(listOf("git", "--version"), ioEnvironment)
        assertEquals(0, result)
        assertTrue(ioEnvironment.outputStream.toString().startsWith("git version"))
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    fun executeCorrectWithEnvironment() {
        val command = ExternalCommand()
        Environment.put("hi", "Salam lije!")

        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(1)), ByteArrayOutputStream(), ByteArrayOutputStream())

        val result = command.execute(listOf("/bin/bash", "-c", "echo \$hi"), ioEnvironment)
        assertEquals(0, result)
        assertEquals("Salam lije!\n", ioEnvironment.outputStream.toString())
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }

    @Test
    fun executeWrong() {
        val command = ExternalCommand()

        val ioEnvironment =
            IOEnvironment(ByteArrayInputStream(ByteArray(1)), ByteArrayOutputStream(), ByteArrayOutputStream())

        val result = command.execute(listOf("git", "--verson"), ioEnvironment)
        assertEquals(-1, result)
        assertTrue(ioEnvironment.errorStream.toString().isNotEmpty())
    }
}
