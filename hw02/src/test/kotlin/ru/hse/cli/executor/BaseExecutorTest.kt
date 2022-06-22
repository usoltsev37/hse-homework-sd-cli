package ru.hse.cli.executor

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import ru.hse.cli.Environment

abstract class BaseExecutorTest {

    @BeforeEach
    fun setUp() {
        Environment.isShutdowned = false
        Environment.vars.clear()
    }
}
