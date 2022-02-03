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

    private fun write4BytesToBuffer(data: Int): ByteArray  {
        val buffer = ByteArray(4)
        buffer[0] = (data shr 0).toByte()
        buffer[1] = (data shr 8).toByte()
        buffer[2] = (data shr 16).toByte()
        buffer[3] = (data shr 24).toByte()
        return buffer
    }

    private fun write8BytesToBuffer(data: Long): ByteArray  {
        val buffer = ByteArray(8)
        buffer[0] = (data shr 0).toByte()
        buffer[1] = (data shr 8).toByte()
        buffer[2] = (data shr 16).toByte()
        buffer[3] = (data shr 24).toByte()
        buffer[4] = (data shr 32).toByte()
        buffer[5] = (data shr 40).toByte()
        buffer[6] = (data shr 48).toByte()
        buffer[7] = (data shr 56).toByte()
        return buffer
    }
}
