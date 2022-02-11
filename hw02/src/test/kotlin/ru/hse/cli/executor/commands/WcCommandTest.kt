package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.hse.cli.executor.BaseExecutorTest
import ru.hse.cli.executor.IOEnvironment
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream
import kotlin.io.path.pathString
import kotlin.io.path.writeText

internal class WcCommandTest: BaseExecutorTest() {
    @Test
    fun executeFile() {
        val wcCommand = WcCommand()
        val fileMessage = "I'll be seeing you again\n" +
                "I'll be seeing you in hell"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)
        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, wcCommand.execute(listOf(file.pathString), ioEnvironment))
        Assertions.assertEquals(
            listOf(2, 11, 51),
            WcCommand.stringToIntArray(ioEnvironment.outputStream.toString()).asList()
        )
    }

    @Test
    fun stringToIntArrayCorrect() {
        Assertions.assertEquals(listOf(12, 432, 10000), WcCommand.stringToIntArray("12 432 10000").toList())
    }
}
