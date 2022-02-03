package ru.hse.cli.executor

import ru.hse.cli.exception.CommandIsNotFoundException
import ru.hse.cli.executor.commands.AbstractCommand
import ru.hse.cli.executor.commands.CatCommand
import ru.hse.cli.executor.commands.PwdCommand

/**
 * Static storage that stores the map from a command name to an instance of the class.
 */
object CommandStorage {
    private var storage: HashMap<String, AbstractCommand> = hashMapOf(
        "cat" to CatCommand(),
        "pwd" to PwdCommand()
    )

    /**
     * Get the instance of the command class by a command name.
     * @param commandName
     * @return the instance of the class.
     * @exception CommandIsNotFoundException will be raised if the given command doesn't exist in the storage.
     */
    fun getCommand(commandName: String): AbstractCommand {
        return storage[commandName] ?: throw CommandIsNotFoundException("Command '$commandName' is not found!")
    }
}
