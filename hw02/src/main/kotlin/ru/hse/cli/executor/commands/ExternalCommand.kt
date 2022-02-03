package ru.hse.cli.executor.commands

import ru.hse.cli.Environment
import ru.hse.cli.executor.IOEnvironment
import java.io.File

/**
 * Represents a call of external command.
 */
class ExternalCommand : AbstractCommand {
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        val processBuilder = ProcessBuilder(args)
        val env: MutableMap<String, String> = processBuilder.environment()

        for ((variable, value) in Environment.vars) {
            env[variable] = value
        }

        val outputFile = File.createTempFile("tmp", null)
        val errorFile = File.createTempFile("tmp", null)
        processBuilder.redirectOutput(outputFile)
        processBuilder.redirectError(errorFile)

        processBuilder.start().waitFor()

        ioEnvironment.outputStream.write(outputFile.readBytes())
        ioEnvironment.errorStream.write(errorFile.readBytes())

        return if (ioEnvironment.errorStream.toString().isEmpty()) {
            0
        } else {
            -1
        }
    }

}
