package ru.hse.cli.executor.commands

import org.apache.commons.io.IOUtils
import ru.hse.cli.executor.IOEnvironment
import java.io.File
import java.io.IOException
import java.lang.Integer.max
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.io.path.createTempFile
import kotlin.io.path.writeText

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
        if (args.isNotEmpty()) {
            ioEnvironment.inputStream.reset()
        }

        return if (forEachExecute(args, ioEnvironment) == -1) -1 else 0

//        var result = 0
//        if (args.isEmpty()) {
//            val data = ioEnvironment.inputStream.toString().split(" ")
//            result = if (forEachExecute(data, ioEnvironment) == -1) -1 else result
//        } else {
//            result = if (forEachExecute(args, ioEnvironment) == -1) -1 else result
//        }
//
//        return result
    }

    private fun forEachExecute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        for (arg in args) {
            val file = File(arg)
            if (!file.exists()) {
                ioEnvironment.errorStream.write("wc: $arg: There is no such file".toByteArray())
                continue
            }

            val result = processFile(file.readLines(), ioEnvironment)

            if (result == -1) {
                return -1
            }
        }

        val content = IOUtils.toString(ioEnvironment.inputStream, StandardCharsets.UTF_8)
        if (content.isNotEmpty() || (content.isEmpty() && args.isEmpty())) {
            return processFile(if (content.isEmpty()) emptyList() else content.split("\n"), ioEnvironment)
        }

        return 0
    }

    private fun processFile(fileLines: List<String>, ioEnvironment: IOEnvironment): Int {
        val cntLines = fileLines.size
        val cntWords = fileLines.stream().mapToInt { if (it.isEmpty()) 0 else it.split("\\s+".toRegex()).size }.sum()
        val cntBytes = fileLines.map { it.encodeToByteArray() }.stream().mapToInt(ByteArray::size).sum() +
                max(0, cntLines - 1) // file.length().toInt()

        try {
            val str = "$cntLines $cntWords $cntBytes"
            ioEnvironment.outputStream.write(str.toByteArray())
        } catch (e: IOException) {
            return -1
        }

        return 0
    }

    companion object {
        fun stringToIntArray(s: String): IntArray {
            return s.split(" ").map { t -> t.toInt() }.toIntArray()
        }
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
