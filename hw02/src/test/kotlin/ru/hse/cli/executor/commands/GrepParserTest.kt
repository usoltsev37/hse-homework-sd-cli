package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.hse.cli.executor.IOEnvironment
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.io.path.pathString
import kotlin.io.path.writeText

internal class GrepParserTest {

    @Test
    fun parseTestI() {
        val grepParser = GrepParser()
        grepParser.parse(listOf("needle", "-i"))
        assertTrue(grepParser.ignoreCase)
        assertFalse(grepParser.wordRegexp)
        assertEquals(0, grepParser.afterContext)
    }

    @Test
    fun parseTestW() {
        val grepParser = GrepParser()
        grepParser.parse(listOf("needle", "-w"))
        assertFalse(grepParser.ignoreCase)
        assertTrue(grepParser.wordRegexp)
        assertEquals(0, grepParser.afterContext)
    }

    @Test
    fun parseTestA() {
        val grepParser = GrepParser()
        grepParser.parse(listOf("needle", "-A", "5"))
        assertFalse(grepParser.ignoreCase)
        assertFalse(grepParser.wordRegexp)
        assertEquals(5, grepParser.afterContext)
    }

    @Test
    fun parseTestFile() {
        val grepParser = GrepParser()
        grepParser.parse(listOf("needle", "haystack.txt"))
        assertFalse(grepParser.ignoreCase)
        assertFalse(grepParser.wordRegexp)
        assertEquals(0, grepParser.afterContext)
        assertEquals("needle", grepParser.needle)
        assertEquals("haystack.txt", grepParser.inputFile)
    }

    @Test
    fun parseTest() {
        val grepParser = GrepParser()
        grepParser.parse(listOf("needle"))
        assertFalse(grepParser.ignoreCase)
        assertFalse(grepParser.wordRegexp)
        assertEquals(0, grepParser.afterContext)
        assertEquals("needle", grepParser.needle)
        grepParser.inputFile?.let { assertTrue(it.isEmpty()) }
    }
}
