package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.hse.cli.executor.IOEnvironment
import java.io.*

internal class PwdCommandTest {

    @Test
    fun testExecuteResult() {
        val pwdCommand = PwdCommand()
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(outputStream, errorStream)
        assertEquals(0, pwdCommand.execute(listOf(), ioEnvironment))
    }

    @Test
    fun testExecuteOutput() {
        val pwdCommand = PwdCommand()
        val expected = System.getProperty("user.dir")
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(outputStream, errorStream)
        pwdCommand.execute(listOf(), ioEnvironment)
        assertEquals(expected, ioEnvironment.outputStream.toString())
    }

    @Test
    fun testExecuteError() {
        val pwdCommand = PwdCommand()
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(outputStream, errorStream)
        pwdCommand.execute(listOf(), ioEnvironment)
        assertTrue(ioEnvironment.errorStream.toString().isEmpty())
    }
}
