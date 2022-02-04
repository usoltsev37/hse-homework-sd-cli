package ru.hse.cli.executor.commands

import ru.hse.cli.Environment
import ru.hse.cli.executor.IOEnvironment
import java.io.File

/**
 * Represents a call of external command.
 */
class ExternalCommand : AbstractCommand {

    /**
     * Execute external command with arguments [args] and IO environment [ioEnvironment].
     * Execution can be unsuccessful if at least one file doesn't exitst.
     * @param args file names to process.
     * @param ioEnvironment stores output and error streams to print a result or error message during execution.
     * @return 0 if execution was successful, -1 otherwise.
     */
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
