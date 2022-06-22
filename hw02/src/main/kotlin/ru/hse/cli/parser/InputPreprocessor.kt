package ru.hse.cli.parser

import ru.hse.cli.Environment

/**
 * Represents string processing with substitution of variables from [Environment]
 */
object InputPreprocessor {

    /**
     * Perform substitution of variables from [Environment] into [input] string
     */
    fun substitute(input: String): String {
        var doubleQuoteMode = false
        var singleQuoteMode = false

        val result = StringBuilder()
        val len = input.length
        var i = 0
        while (i < len) {
            val currentChar = input[i]

            if (currentChar == '"' && !singleQuoteMode) {
                doubleQuoteMode = !doubleQuoteMode
            }

            if (currentChar == '\'' && !doubleQuoteMode) {
                singleQuoteMode = !singleQuoteMode
            }

            if (currentChar == '$' && !singleQuoteMode) {
                var varName = ""
                i++
                while (i < len && input[i] != '"' && input[i] != '\'' && input[i] != '=' &&
                    input[i] != '|' && input[i] != '$' && input[i] != ' ') {
                    varName += input[i]
                    i++
                }
                result.append(Environment.vars[varName] ?: "")
                continue
            }

            result.append(currentChar)
            i++
        }

        return result.toString()
    }
}
