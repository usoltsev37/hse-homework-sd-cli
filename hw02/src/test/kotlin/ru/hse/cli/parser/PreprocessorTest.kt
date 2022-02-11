package ru.hse.cli.parser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.hse.cli.Environment

class PreprocessorTest {

    @Test
    fun testSingleSubstitution() {
        Environment.vars["abc"] = "aaaa"
        Assertions.assertEquals("echo aaaa", InputPreprocessor.substitute("echo \$abc"))
    }

    @Test
    fun testMultipleSubstitution() {
        Environment.vars["x"] = "ec"
        Environment.vars["y"] = "ho"
        Assertions.assertEquals("echo", InputPreprocessor.substitute("\$x\$y"))
    }

    @Test
    fun testDoubleQuotes() {
        Environment.vars["a"] = "lala"
        Assertions.assertEquals("wc \" ala 'lala'\"", InputPreprocessor.substitute("wc \" ala '\$a'\""))
    }

    @Test
    fun testSingleQuotes() {
        Environment.vars["ta"] = "sha"
        Assertions.assertEquals("echo 'qdq\"\$ta\"fw'", InputPreprocessor.substitute("echo 'qdq\"\$ta\"fw'"))
    }

    @Test
    fun testQuotes() {
        Environment.vars["ta"] = "sha"

        Assertions.assertEquals("echo 'qdq\$ta fw'", InputPreprocessor.substitute("echo 'qdq\$ta fw'"))
        Assertions.assertEquals("wc \" ala sha\"", InputPreprocessor.substitute("wc \" ala \$ta\""))
    }

    @Test
    fun testNoSuchVar() {
        Assertions.assertEquals("echo ", InputPreprocessor.substitute("echo \$a"))
        Assertions.assertEquals("cat \"\"", InputPreprocessor.substitute("cat \"\$aa\""))
    }
}
