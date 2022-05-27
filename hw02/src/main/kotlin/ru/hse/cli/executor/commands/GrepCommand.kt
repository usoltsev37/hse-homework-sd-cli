package ru.hse.cli.executor.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import org.apache.commons.io.IOUtils
import ru.hse.cli.executor.IOEnvironment
import java.io.File
import java.lang.Integer.max
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * Represents the command grep
 */
class GrepCommand : AbstractCommand {

    /**
     * Parser for arguments of the GrepCommand.
     * @key [-w] (word-regexp) - search by whole words.
     * @key [-i] (ignore-case) - case-insensitive search.
     * @key [-A NUM] (after-context) - prints NUM strings after matched string.
     */
    class GrepParser : CliktCommand() {
        val needle by argument()
        val inputFile by argument().optional()
        val ignoreCase by option(
            names = arrayOf(
                "-i",
                "--ignore-case"
            ),
            help = "Case-insensitive search"
        ).flag()
        val wordRegexp by option(
            names = arrayOf(
                "-w",
                "--word-regexp"
            ),
            help = "Search by whole words"
        ).flag()
        val afterContext by option(
            names = arrayOf(
                "-A",
                "--after-context"
            ),
            help = "The number of lines after match which should be printed. Every line will be printed at most one time"
        ).int().default(0)

        override fun run() = Unit
    }

    /**
     * Execute grep command with arguments [args] and IO environment [ioEnvironment].
     * Execution can be unsuccessful if at least one file doesn't exist.
     * @param args arguments to process.
     * @param ioEnvironment stores output and error streams to print a result or error message during execution.
     * @return 0 if execution was successful, -1 otherwise.
     */
    override fun execute(args: List<String>, ioEnvironment: IOEnvironment): Int {
        val grepParser = GrepParser().apply { parse(args) }
        with(grepParser) {
            require(afterContext >= 0) {
                "grep: ${afterContext}: wrong argument of context length."
            }
        }

        var needle = grepParser.needle.toRegex()
        val content: List<String> = if (grepParser.inputFile == null) {
            IOUtils.toString(ioEnvironment.inputStream, StandardCharsets.UTF_8).split("\n")
        } else {
            val file = File(grepParser.inputFile!!)
            file.readLines()
        }

        var matchResults: MutableList<String> = content.toMutableList()

        if (grepParser.ignoreCase) {
            needle = grepParser.needle.lowercase(Locale.getDefault()).toRegex()
            matchResults = content.filter {
                needle.find(it.lowercase(Locale.getDefault())) != null
            }.toMutableList()
        }

        if (!grepParser.ignoreCase && !grepParser.wordRegexp) {
            matchResults = matchResults.filter {
                needle.find(it) != null
            }.toMutableList()
        }

        if (grepParser.wordRegexp) {
            needle = "\\b${needle.pattern}\\b".toRegex()
            matchResults = matchResults.filter {
                needle.find(if (grepParser.ignoreCase) it.lowercase(Locale.getDefault()) else it) != null
            }.toMutableList()
        }

        var matchedIndex = 0
        val segments: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in content.indices) {
            if (matchedIndex <= matchResults.lastIndex && content[i] == matchResults[matchedIndex]) {
                segments.add(Pair(i, i + grepParser.afterContext))
                matchedIndex++
            }

            if (matchedIndex == matchResults.size)
                break
        }

        val processedIndex = processSegments(segments)
        matchResults = content.filterIndexed { index, _ -> index in processedIndex }.toMutableList()

        ioEnvironment.outputStream.write(matchResults.joinToString("\n").toByteArray())

        return 0
    }

    private fun processSegments(segments: MutableList<Pair<Int, Int>>): List<Int> {
        val matchResults = mutableListOf<Int>()
        var currentRightBorder = -1
        for (i in segments.indices) {
            val segment = segments[i]

            if (segment.first > currentRightBorder) {
                for (ind in segment.first..segment.second)
                    matchResults.add(ind)
            } else {
                for (ind in segment.first..max(currentRightBorder, segment.second)) {
                    matchResults.add(ind)
                }
            }

            currentRightBorder = max(currentRightBorder, segment.second)
        }

        return matchResults
    }
}
