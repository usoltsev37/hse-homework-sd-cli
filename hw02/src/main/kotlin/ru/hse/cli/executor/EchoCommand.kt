package ru.hse.cli.executor

import java.io.IOException
import java.util.*

/**
 * The command that displays its argument (or arguments)
 */
class EchoCommand : AbstactCommand {
    /**
     * Transform args in one String separated by space and write it in io.outputStream in UTF8
     * @return 0 if success
     * @return 1 if fail
     */
    override fun execute(args: List<String>, io: IOEnvironment): Int {
        try {
            val arr: Array<String> = Array(args.size) { i -> args[i] }
            io.outputStream.write(arr.joinToString(separator = " ").toString().toByteArray())
        } catch (e: IOException) {
            return 1
        }
        return 0
    }
}
