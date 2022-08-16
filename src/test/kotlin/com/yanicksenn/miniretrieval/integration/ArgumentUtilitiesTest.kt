package com.yanicksenn.miniretrieval.integration

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ArgumentUtilitiesTest {

    @Test
    fun `should keep empty string`() {
        assertEquals(emptyList<String>(), "".toArgs().asList())
    }

    @Test
    fun `should parse single word arguments`() {
        assertEquals(listOf("mvn", "clean", "install"), "mvn clean install".toArgs().asList())
    }

    @Test
    fun `should ignore spaces`() {
        assertEquals(listOf("mvn", "clean"), "mvn   clean".toArgs().asList())
    }

    @Test
    fun `should ignore tabs`() {
        assertEquals(listOf("mvn", "clean"), "mvn\t\t\tclean".toArgs().asList())
    }

    @Test
    fun `should concat quoted strings`() {
        assertEquals(listOf("echo", "Hello, World!"), "echo \"Hello, World!\"".toArgs().asList())
    }

    @Test
    fun `should concat multiple quoted strings`() {
        assertEquals(listOf("echo", "Hello, World!", "Good bye!"), "echo \"Hello, World!\" \"Good bye!\"".toArgs().asList())
    }
}