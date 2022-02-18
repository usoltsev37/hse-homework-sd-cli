package ru.hse.cli

import ru.hse.cli.exception.UnknownCommandException
import ru.hse.cli.executor.Executor
import ru.hse.cli.executor.IOEnvironment
import ru.hse.cli.parser.Parser
import ru.hse.cli.parser.impl.ParserImpl
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

fun main() {
    while (!Environment.isShutdowned) {
        try {
            val rawCommand: String = readLine()!!

            if (rawCommand.isEmpty()) {
                continue
            }

            val parser: Parser = ParserImpl(rawCommand)
            val command = parser.parse()

            val ioEnvironment = IOEnvironment(
                inputStream = ByteArrayInputStream(ByteArray(0)),
                outputStream = ByteArrayOutputStream(),
                errorStream = ByteArrayOutputStream()
            )

            val executor = Executor(command, ioEnvironment)
            executor.execute()

            ioEnvironment.printState()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: UnknownCommandException) {
            println("Unknown command: ${e.name}")
        }
    }
}
