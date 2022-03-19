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

    @Test
    fun parseTestIgnore() {
        val grepCommand = GrepCommand()
        val fileMessage = "I'm on the highway to hell\n" +
                "On the s43needle highway to hell\n" +
                "Highway to hell\n" +
                "I'm on the highway to hell nEEdlE123"
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)

        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("needle", file.pathString, "-i"), ioEnvironment))
        Assertions.assertEquals(
            "On the s43needle highway to hell\n" +
                    "I'm on the highway to hell nEEdlE123",
            ioEnvironment.outputStream.toString()
        )
    }

    @Test
    fun parseTestAfterContextUsual() {
        val grepCommand = GrepCommand()
        val fileMessage = """
            a
            b
            c
            d
            a
            d
            c
        """.trimIndent()
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)

        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("a", file.pathString, "-A", "1"), ioEnvironment))
        Assertions.assertEquals(
            """
                a
                b
                a
                d
            """.trimIndent(),
            ioEnvironment.outputStream.toString()
        )
    }

    @Test
    fun parseTestAfterContextAfterEnd() {
        val grepCommand = GrepCommand()
        val fileMessage = """
            a
            b
            c
            d
            a
            b
            c
            a
        """.trimIndent()
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)

        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("a", file.pathString, "-A", "1"), ioEnvironment))
        Assertions.assertEquals(
            """
                a
                b
                a
                b
                a
            """.trimIndent(),
            ioEnvironment.outputStream.toString()
        )
    }

    @Test
    fun parseTestAfterContextOverlap() {
        val grepCommand = GrepCommand()
        val fileMessage = """
            b
            a
            c
            a
            b
            a
            e
            e
            c
            z
            f
            d
        """.trimIndent()
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)

        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("a", file.pathString, "-A", "3"), ioEnvironment))
        Assertions.assertEquals(
            """
                a
                c
                a
                b
                a
                e
                e
                c
            """.trimIndent(),
            ioEnvironment.outputStream.toString()
        )
    }

    @Test
    fun parseTestAfterContextHard() {
        val grepCommand = GrepCommand()
        val fileMessage = """
            b
            a
            c
            a
            b
            d
            e
            e
            a
            z
            f
            a
            b
        """.trimIndent()
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)

        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("a", file.pathString, "-A", "3"), ioEnvironment))
        Assertions.assertEquals(
            """
                a
                c
                a
                b
                d
                e
                a
                z
                f
                a
                b
            """.trimIndent(),
            ioEnvironment.outputStream.toString()
        )
    }

    @Test
    fun parseTestIgnoreWordReg() {
        val grepCommand = GrepCommand()
        val fileMessage = """
            I'm on the highway to 6hell42
            Highway to HeLl
            I'm on the highway to HELL
            Highway to hello
            Don't stop me
        """.trimIndent()
        val file = kotlin.io.path.createTempFile()
        file.writeText(fileMessage)

        val inputStream = ByteArrayInputStream(ByteArray(0))
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val ioEnvironment = IOEnvironment(inputStream, outputStream, errorStream)

        Assertions.assertEquals(0, grepCommand.execute(listOf("HELL", file.pathString, "-w", "-i"), ioEnvironment))
        Assertions.assertEquals(
            """
            Highway to HeLl
            I'm on the highway to HELL
        """.trimIndent(),
            ioEnvironment.outputStream.toString()
        )
    }
}
