package ru.hse.cli

/**
 * Static storage that stores the flag whether the CLI is shutdowned.
 */
object Environment {

    val vars: HashMap<String, String> = HashMap()
    var isShutdowned: Boolean = false
}
