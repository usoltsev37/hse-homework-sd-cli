package ru.hse.cli.parser.util

/**
 * Representation of the command being entered into shell
 */
data class Command(val name: String = "Unknown Command", val args: List<String> = emptyList())
