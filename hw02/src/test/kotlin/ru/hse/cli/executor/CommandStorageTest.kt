package ru.hse.cli.executor

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.hse.cli.executor.commands.CatCommand
import ru.hse.cli.executor.commands.ExternalCommand
import kotlin.test.assertFailsWith

internal class CommandStorageTest {

    @Test
    fun getCommand() {
        val expected = CatCommand()
        assertTrue(expected.javaClass == CommandStorage.getCommand("cat").javaClass)
    }

    @Test
    fun getCommandNotFound() {
        val expected = ExternalCommand()
        assertTrue(expected.javaClass == CommandStorage.getCommand("open").javaClass)
    }
}
