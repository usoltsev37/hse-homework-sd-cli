package ru.hse.cli

import ru.hse.cli.exception.LexerException
import ru.hse.cli.exception.ParserException
import ru.hse.cli.exception.UnknownCommandException
import ru.hse.cli.executor.Executor
import ru.hse.cli.executor.IOEnvironment
import ru.hse.cli.parser.InputPreprocessor
import ru.hse.cli.parser.Parser
import ru.hse.cli.parser.impl.ParserImpl
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

fun main() {
    while (!Environment.isShutdowned) {
        try {
            val rawCommand: String = readLine()!!
            val preprocessedRawCommand = InputPreprocessor.substitute(rawCommand)

            if (preprocessedRawCommand.isEmpty()) {
                continue
            }

            val parser: Parser = ParserImpl(preprocessedRawCommand)
            val commands = parser.parse()

            val ioEnvironment = IOEnvironment(
                inputStream = ByteArrayInputStream(ByteArray(0)),
                outputStream = ByteArrayOutputStream(),
                errorStream = ByteArrayOutputStream()
            )

            val executor = Executor(commands, ioEnvironment)
            executor.execute()

            ioEnvironment.printState()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: UnknownCommandException) {
            println("Unknown command: ${e.message}")
        } catch (e: LexerException) {
            println(e.message)
        } catch (e: ParserException) {
            println(e.message)
        }
    }
}
