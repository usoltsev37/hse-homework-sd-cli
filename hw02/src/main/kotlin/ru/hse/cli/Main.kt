package ru.hse.cli

import ru.hse.cli.executor.Executor
import ru.hse.cli.executor.IOEnvironment
import ru.hse.cli.parser.Parser
import ru.hse.cli.parser.impl.ParserImpl
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

fun main(args: Array<String>) {
    while (!Environment.isShutdowned) {
        val rawCommand: String = readLine()!!

        if (rawCommand.isEmpty()) {
            continue
        }

        val parser: Parser = ParserImpl(rawCommand)
        val command = parser.parse()

        val ioEnvironment = IOEnvironment(
            inputStream = ByteArrayInputStream(ByteArray(42)),
            outputStream = ByteArrayOutputStream(),
            errorStream = ByteArrayOutputStream()
        )

        val executor = Executor(command, ioEnvironment)
        executor.execute()

        ioEnvironment.printState()
    }
}
