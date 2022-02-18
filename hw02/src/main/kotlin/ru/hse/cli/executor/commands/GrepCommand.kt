package ru.hse.cli.executor.commands

import kotlinx.cli.*
import org.apache.commons.io.IOUtils
import ru.hse.cli.executor.IOEnvironment
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList

class GrepParser {
    val parser = ArgParser("")
    val needle by parser.argument(ArgType.String, description = "Needle")
    val inputFile by parser.argument(ArgType.String, description = "Input file").optional()
    val wordRegexp by parser.option(
        ArgType.Boolean,
        fullName = "word-regexp",
        shortName = "w",
        description = "Search by whole words"
    )
        .default(false)
    val ignoreCase by parser.option(
        ArgType.Boolean,
        fullName = "ignore-case",
        shortName = "i",
        description = "Case-insensitive search"
    )
        .default(false)
    val afterContext by parser.option(
        ArgType.Int,
        fullName = "after-context",
        shortName = "A",
        description = "Very hard to explain"
    ).default(0)

    fun parse(args: List<String>) {
        parser.parse(args.toTypedArray())
        return // обработать ошибку
    }
}

class GrepCommand : AbstractCommand {
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        val grepParser = GrepParser()
        grepParser.parse(args)

        var needle = grepParser.needle.toRegex()
        val content: List<String> = if (grepParser.inputFile == null) {
            IOUtils.toString(ioEnvironment.inputStream, StandardCharsets.UTF_8).split("\n")
        } else {
            val file = File(grepParser.inputFile!!)
            file.readLines()
        }

        var matchResults: List<String>


        if (grepParser.wordRegexp) {
            needle = "\\b${grepParser.needle}\\b".toRegex()
        }

        if (grepParser.ignoreCase) {
            needle = grepParser.needle.lowercase(Locale.getDefault()).toRegex()
//            matchResults.addAll(content.filter {
//                needle.find(it.lowercase(Locale.getDefault())) != null
//            })
        }

        if (grepParser.afterContext != 0) {

        }

        matchResults = content.filter {
            needle.find(it) != null
        }

        ioEnvironment.outputStream.write(matchResults.joinToString("\n").toByteArray())

        return 0
    }
}
