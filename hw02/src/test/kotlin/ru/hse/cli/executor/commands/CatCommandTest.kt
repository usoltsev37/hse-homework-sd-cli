package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.hse.cli.executor.IOEnvironment
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Path
import kotlin.io.path.pathString
import kotlin.io.path.writeText

internal class CatCommandTest {

    @Test
    fun executeCorrectOneFile() {
        val catCommand = CatCommand()
        val message = "Hello World!"
        val file = kotlin.io.path.createTempFile()
        file.writeText(message)

        val inputStream = ByteArrayInputStream(ByteArray(1))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        assertEquals(0, catCommand.execute(listOf(file.pathString), ioEnvironment))
        assertEquals(message, ioEnvironment.outputStream.toString())
    }

    @Test
    fun executeCorrectManyFiles() {
        val catCommand = CatCommand()
        val messages = listOf("Hello World!", "The world's my oyster. I'm the pearl.", "Kotlin here!")
        val files = mutableListOf<Path>()

        repeat(3) {
            files.add(kotlin.io.path.createTempFile())
        }
        for (i in 0 until 3) {
            files[i].writeText(messages[i])
        }

        val inputStream = ByteArrayInputStream(ByteArray(1))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        val result = catCommand.execute(files.map { it.pathString }, ioEnvironment)
        assertEquals(0, result)

        val outputStreamMessage = ioEnvironment.outputStream.toString()
        assertEquals(outputStreamMessage, messages.reduce {acc, string -> acc + string})
    }

    @Test
    fun executeFailureFileNotExist() {
        val catCommand = CatCommand()
        val inputStream = ByteArrayInputStream(ByteArray(1))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        val filename = "thisfiledoesntexist.txt"
        val result = catCommand.execute(listOf(filename), ioEnvironment)
        assertNotEquals(0, result)

        assertEquals("File '$filename' does not exist!", ioEnvironment.errorStream.toString())
    }
}
