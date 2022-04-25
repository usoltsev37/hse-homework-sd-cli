package ru.hse.cli.executor.commands

import ru.hse.cli.executor.IOEnvironment
import java.io.IOException

/**
 * The command that displays its argument (or arguments)
 */
class EchoCommand : AbstractCommand {
    /**
     * Transform args in one String separated by space and write it in io.outputStream in UTF8
     * @return 0 if success
     * @return 1 if fail
     */
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        try {
            val array: Array<String> = Array(args.size) { i -> args[i] }
            ioEnvironment.outputStream.write(array.joinToString(separator = " ").toByteArray())
        } catch (e: IOException) {
            return 1
        }
        return 0
    }
}
