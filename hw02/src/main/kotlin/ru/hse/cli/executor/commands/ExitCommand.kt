package ru.hse.cli.executor.commands

import ru.hse.cli.Environment
import ru.hse.cli.executor.IOEnvironment
import java.io.IOException

/**
 * The command that ignores inputStream, clears outputStream, set isShutdowned in Environment in true
 */
class ExitCommand : AbstractCommand {
    /**
     * Set isShutdowned in Environment in true and clear io.outputStream
     * @return 1 if success
     * @return 0 if fail
     */
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        Environment.isShutdowned = true
        try {
            ioEnvironment.outputStream.flush()
        } catch (e: IOException) {
            return 0
        }
        return 1
    }
}
