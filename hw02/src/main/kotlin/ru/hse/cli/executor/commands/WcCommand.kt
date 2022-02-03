package ru.hse.cli.executor.commands

import ru.hse.cli.executor.IOEnvironment
import java.io.File
import java.io.IOException
import java.util.*
import java.util.UUID

/**
 * The command that tries to find a file from inputStream, if it does not find,
 * then creates a temporary file, writes to outputStream
 * the number of lines, words and bytes of the file
 */
class WcCommand : AbstractCommand {
    /**
     * For every argument try to open file,
     * if argument is not a filename, create a temporary file and write argument in it
     * writes to outputStream the number of lines, words and bytes of the file
     * @return 1 if success
     * @return 0 if fail
     * @return 2 if file not found or can
     */
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        for (arg in args) {
            var file = File(arg)
            if (!file.exists()) {
                file = File(UUID.randomUUID().toString())
                if (!file.createNewFile()) {
                    // файл не создался
                    return 2
                }
                file.writeText(arg)
            }

            val cntLines = getCntLines(file)
            val cntWords = getCntWords(file)
            val cntBytes = file.length()

            try {
                ioEnvironment.outputStream.write(write4BytesToBuffer(cntLines))
                ioEnvironment.outputStream.write(write4BytesToBuffer(cntWords))
                ioEnvironment.outputStream.write(write8BytesToBuffer(cntBytes))
            } catch (e: IOException) {
                return 0
            }
            return 1
        }
        TODO("Not yet implemented")
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
