package ru.hse.cli.executor

import java.io.OutputStream

/**
 * Stores streams for an interaction between a command and IO.
 */
class IOEnvironment constructor(val outputStream: OutputStream, val errorStream: OutputStream) {

    fun printState() {
        if (errorStream.toString().isEmpty()) {
            println(outputStream.toString())
        } else {
            println("Error: ")
            println(errorStream.toString())
        }
    }
}
