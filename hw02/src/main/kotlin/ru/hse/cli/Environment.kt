package ru.hse.cli

object Environment {

    /**
     * Stores variables with its values
     */
    val vars: HashMap<String, String> = hashMapOf()

    /**
     * Is CLI shutdowned i.e exit command was executed
     */
    var isShutdowned: Boolean = false

    fun put(variable: String, value: String) {
        vars[variable] = value
    }
}
