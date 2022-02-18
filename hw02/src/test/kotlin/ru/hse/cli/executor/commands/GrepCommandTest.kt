package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.hse.cli.executor.BaseExecutorTest
import ru.hse.cli.executor.IOEnvironment
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.io.path.pathString
import kotlin.io.path.writeText

internal class GrepCommandTest : BaseExecutorTest() {
    @Test
    fun parseTestFullMatchOne() {
        val grepCommand = GrepCommand()
        val fileMessage = "I'll be seeing you again\n" +
                "I'll be seeing you in hell"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)
        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("hell", file.pathString, "-w"), ioEnvironment))
        Assertions.assertEquals(
            "I'll be seeing you in hell",
            ioEnvironment.outputStream.toString()
        )
    }

    @Test
    fun parseTestFullMatchNothing() {
        val grepCommand = GrepCommand()
        val fileMessage = "I'll be seeing you again\n" +
                "I'll be seeing you in hell"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)
        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("b", file.pathString, "-w"), ioEnvironment))
        Assertions.assertTrue(ioEnvironment.outputStream.toString().isEmpty())
    }

    @Test
    fun parseTestFullMatchAll() {
        val grepCommand = GrepCommand()
        val fileMessage = "I'll be seeing you again\n" +
                "I'll be seeing you in hell"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)
        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("be", file.pathString, "-w"), ioEnvironment))
        Assertions.assertEquals(fileMessage, ioEnvironment.outputStream.toString())
    }

    @Test
    fun parseTestAll() {
        val grepCommand = GrepCommand()
        val fileMessage = "I'll be seeing you again\n" +
                "I'll be seeing you in hell"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)
        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("ll", file.pathString), ioEnvironment))
        Assertions.assertEquals(fileMessage, ioEnvironment.outputStream.toString())
    }

    @Test
    fun parseTestNothing() {
        val grepCommand = GrepCommand()
        val fileMessage = "I'll be seeing you again\n" +
                "I'll be seeing you in hell"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)
        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("z", file.pathString), ioEnvironment))
        Assertions.assertTrue(ioEnvironment.outputStream.toString().isEmpty())
    }

    @Test
    fun parseTestOne() {
        val grepCommand = GrepCommand()
        val fileMessage = "I'll be seeing you again\n" +
                "I'll be seeing you in hell"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)
        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("aga", file.pathString), ioEnvironment))
        Assertions.assertEquals(
            "I'll be seeing you again",
            ioEnvironment.outputStream.toString()
        )
    }
}
