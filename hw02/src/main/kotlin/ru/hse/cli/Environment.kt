package ru.hse.cli

/**
 * Static storage that stores the flag whether the CLI is shutdowned.
 */
object Environment {

    val vars: HashMap<String, String> = hashMapOf()

    var isShutdowned: Boolean = false

    fun put(variable: String, value: String) {
        vars[variable] = value
    }
}
