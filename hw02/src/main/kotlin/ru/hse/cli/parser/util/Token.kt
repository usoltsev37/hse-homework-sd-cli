package ru.hse.cli.parser.util

import java.util.regex.Pattern

/**
 * Special Lexer tokens
 */
enum class Token(regex: String) {
    TK_PIPE("\\|"),
    TK_SPACE("\\s+"),
    TK_ASSIGN("="),

    TK_STR("[^'\"=\\s]+"),
    TK_STR_IN1("\"[^\"]+\""),
    TK_STR_IN2("\'[^']+\'");


    private val pattern = Pattern.compile("^$regex");

    /**
     * Match given string with pattern
     * @return if a subsequence of the input
     *         sequence matches this matcher's pattern
     *         the offset after the last character matched
     *         else -1
     */
    fun endOfMatch(s: String): Int {
        val matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.end();
        }
        return -1;
    }
}
