package ru.hse.cli.executor

interface AbstactCommand {
    fun execute(args: List<String>, io: IOEnvironment): Int
}
