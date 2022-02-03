package ru.hse.cli.executor

import java.io.OutputStream

/*
 Stores streams for an interaction between a command and IO.
*/
data class IOEnvironment(

    /*
    Stream stores output of a command execution.
    */
    val outputStream: OutputStream,
    /**
     * Stream stores error of a command execution.
     */
    val errorStream: OutputStream
)
