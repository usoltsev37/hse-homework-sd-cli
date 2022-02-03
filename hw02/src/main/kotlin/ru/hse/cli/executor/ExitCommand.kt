package ru.hse.cli.executor

import ru.hse.cli.Environment
import java.io.IOException

/**
 * The command that ignores inputStream, clears outputStream, set isShutdowned in Environment in true
 */
class ExitCommand : AbstactCommand {
    /**
     * Set isShutdowned in Environment in true and clear io.outputStream
     * @return 1 if success
     * @return 0 if fail
     */
    override fun execute(args: List<String>, io: IOEnvironment): Int {
        Environment.isShutdowned = true
        try {
            io.outputStream.flush()
        } catch (e: IOException) {
            return 0
        }
        return 1
    }
}
