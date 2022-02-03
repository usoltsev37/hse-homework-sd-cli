package ru.hse.cli.executor

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.cli.executor.commands.EchoCommand
import java.io.ByteArrayOutputStream
import java.util.*

internal class EchoCommandTest {
    @Test
    fun executeCorrectOneArgument() {
        val echoCommand = EchoCommand()
        val arg = "Die, die, die my darling"
        val ioEnvironment = IOEnvironment(ByteArrayOutputStream(), ByteArrayOutputStream())

        assertEquals(0, echoCommand.execute(Arrays.asList(arg), ioEnvironment))
        assertEquals(arg, ioEnvironment.outputStream.toString())
    }

    @Test
    fun executeCorrectSomeArguments() {

    }
}
