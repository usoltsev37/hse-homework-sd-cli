package ru.hse.cli.parser

import ru.hse.cli.Environment

object InputPreprocessor {

    fun substitute(input: String): String {
        var b1 = false
        var b2 = false

        val result = StringBuilder()
        val len = input.length
        var i = 0
        while (i < len) {
            val c = input[i]
            if (c == '"' && !b2) {
                b1 = !b1
            }
            if (c == '\'' &&  !b1) {
                b2 = !b2
            }
            if (c == '$' && !b2) {
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
            result.append(c)
            i++
        }

        return result.toString()
    }
}
