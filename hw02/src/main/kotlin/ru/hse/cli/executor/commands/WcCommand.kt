package ru.hse.cli.executor.commands

import ru.hse.cli.executor.IOEnvironment
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.io.path.createTempFile

/**
 * The command that tries to find a file from args: List<String>, if it does not find,
 * then creates a temporary file, writes to outputStream
 * the number of lines, words and bytes of the file
 */
class WcCommand : AbstractCommand {
    /**
     * For every argument try to open file,
     * if argument is not a filename, create a temporary file and write argument in it
     * writes to outputStream the number of lines, words and bytes of the file
     * @return 0 if success
     * @return 1 if fail
     * @return 2 if file not found or can
     */
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        var result = 0
        if (args.isEmpty()) {
            val data = ioEnvironment.inputStream.toString().split(" ")
            result = if (forEachExecute(data, ioEnvironment) == -1) -1 else result
        } else {
            result = if (forEachExecute(args, ioEnvironment) == -1) -1 else result
        }

        return result
    }

    private fun forEachExecute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        for (arg in args) {
            var file = File(arg)
            if (!file.exists()) {
                file = createTempFile().toFile()
                file.writeText(arg)
            }

            val cntLines = getCntLines(file)
            val cntWords = getCntWords(file)
            val cntBytes = file.length().toInt()

            try {
                val str = cntLines.toString() + " " + cntWords.toString() + " " + cntBytes
                ioEnvironment.outputStream.write(str.toByteArray())
            } catch (e: IOException) {
                return 1
            }
        }
        return 0
    }

    companion object {
        fun stringToIntArray(s: String): IntArray {
            return s.split(" ").map { t -> t.toInt() }.toIntArray()
        }
    }

    private fun getCntLines(file: File): Int {
        var cntLines = 0
        file.forEachLine {
            cntLines++
        }
        return cntLines
    }

    private fun getCntWords(file: File): Int {
        val scan = Scanner(file.inputStream())
        var cntWords = 0
        while (scan.hasNext()) {
            scan.next()
            cntWords++
        }
        return cntWords
    }
}
