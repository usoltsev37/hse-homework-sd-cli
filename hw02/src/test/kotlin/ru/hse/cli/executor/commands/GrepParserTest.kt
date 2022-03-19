package ru.hse.cli.executor.commands

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GrepParserTest {

    @Test
    fun parseTestI() {
        val grepParser = GrepCommand.GrepParser()
        with(grepParser) {
            parse(listOf("needle", "-i"))
            assertTrue(ignoreCase)
            assertFalse(wordRegexp)
            assertEquals(0, afterContext)
        }
    }

    @Test
    fun parseTestW() {
        val grepParser = GrepCommand.GrepParser()
        with(grepParser) {
            parse(listOf("needle", "-w"))
            assertFalse(ignoreCase)
            assertTrue(wordRegexp)
            assertEquals(0, afterContext)
        }
    }

    @Test
    fun parseTestA() {
        val grepParser = GrepCommand.GrepParser()
        with(grepParser) {
            parse(listOf("needle", "-A", "5"))
            assertFalse(ignoreCase)
            assertFalse(wordRegexp)
            assertEquals(5, afterContext)
        }
    }

    @Test
    fun parseTestFile() {
        val grepParser = GrepCommand.GrepParser()
        with(grepParser) {
            parse(listOf("needle", "haystack.txt"))
            assertFalse(ignoreCase)
            assertFalse(wordRegexp)
            assertEquals(0, afterContext)
            assertEquals("needle", needle)
            assertEquals("haystack.txt", inputFile)
        }
    }

    @Test
    fun parseTest() {
        val grepParser = GrepCommand.GrepParser()
        with(grepParser) {
            parse(listOf("needle"))
            assertFalse(ignoreCase)
            assertFalse(wordRegexp)
            assertEquals(0, afterContext)
            assertEquals("needle", needle)
            grepParser.inputFile?.let { assertTrue(it.isEmpty()) }
        }
    }
}
