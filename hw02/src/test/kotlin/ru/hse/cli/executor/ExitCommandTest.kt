package ru.hse.cli.executor

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.hse.cli.Environment
import ru.hse.cli.executor.commands.ExitCommand
import java.io.ByteArrayOutputStream
import java.util.*

internal class ExitCommandTest {

    @Test
    fun executeCorrect() {
        val exitCommand = ExitCommand()
        val ioEnvironment = IOEnvironment(ByteArrayOutputStream(), ByteArrayOutputStream())

        Assertions.assertFalse(Environment.isShutdowned)
        Assertions.assertEquals(0, exitCommand.execute(Arrays.asList(), ioEnvironment))
        Assertions.assertTrue(Environment.isShutdowned)
    }
}
