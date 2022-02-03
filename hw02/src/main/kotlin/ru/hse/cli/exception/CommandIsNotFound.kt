package ru.hse.cli.exception

/**
 * The CommandIsNotFoundException is raised in case of access to a nonexistent command in CommandStorage.
 */
open class CommandIsNotFoundException(message: String) : Exception(message)
