package ru.hse.cli.executor

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.hse.cli.exception.CommandIsNotFoundException
import ru.hse.cli.executor.commands.CatCommand
import kotlin.test.assertFailsWith

internal class CommandStorageTest {

    @Test
    fun getCommand() {
        val expected = CatCommand()
        assertTrue(expected.javaClass == CommandStorage.getCommand("cat").javaClass)
    }

    @Test
    fun getCommandNotFound() {
        assertFailsWith<CommandIsNotFoundException> { CommandStorage.getCommand("open") }
    }
}
