package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.hse.cli.executor.IOEnvironment
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream
import kotlin.io.path.pathString
import kotlin.io.path.writeText

internal class WcCommandTest {
    @Test
    fun executeFile() {
        val wcCommand = WcCommand()
        val fileMessage = "I'll be seeing you again\n" +
                "I'll be seeing you in hell"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)
        val inputStream = ByteArrayInputStream(ByteArray(1))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, wcCommand.execute(listOf(file.pathString), ioEnvironment))
        Assertions.assertEquals(listOf(2, 11, 51), WcCommand.stringToIntArray(ioEnvironment.outputStream.toString()).asList())
    }

    @Test
    fun executeText() {
        val wcCommand = WcCommand()
        val text = "Die, die, die\n" +
                "Die, die, die\n" +
                "Die, die, die\n" +
                "Die"
        val inputStream = ByteArrayInputStream(ByteArray(1))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, wcCommand.execute(listOf(text), ioEnvironment))
        Assertions.assertEquals(listOf(4, 10, 45), WcCommand.stringToIntArray(ioEnvironment.outputStream.toString()).asList())
    }

    @Test
    fun stringToIntArrayCorrect() {
        Assertions.assertEquals(listOf(12, 432, 10000), WcCommand.stringToIntArray("12 432 10000").toList())
    }
}
