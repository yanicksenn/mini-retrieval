package com.yanicksenn.miniretrieval.utility

import com.yanicksenn.miniretrieval.to.Token
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.io.InputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TokenFileParserTest {

    @Test
    fun `ensure parsing token file contains the correct tokens`() {
        val stopListFile = createInputStreamWithLines(
            "this",
            "that",
            "them")

        TokenFileParser(stopListFile).parse()
            .assertContainsToken("this")
            .assertContainsToken("that")
            .assertContainsToken("them")
            .assertSize(3)
    }

    @Test
    fun `ensure lines starting with # are ignored`() {
        val stopListFile = createInputStreamWithLines(
            "# this",
            "that",
            "# them")

        TokenFileParser(stopListFile).parse()
            .assertDoesNotContainToken("this")
            .assertContainsToken("that")
            .assertDoesNotContainToken("them")
            .assertSize(1)
    }

    @Test
    fun `ensure blank lines are ignored`() {
        val stopListFile = createInputStreamWithLines(
            "this",
            "",
            "   ",
            "them")

        TokenFileParser(stopListFile).parse()
            .assertContainsToken("this")
            .assertContainsToken("them")
            .assertSize(2)
    }

    @Test
    fun `ensure leading and trailing whitespaces are trimmed`() {
        val stopListFile = createInputStreamWithLines(
            "  this  ",
            "that ",
            " them")

        TokenFileParser(stopListFile).parse()
            .assertContainsToken("this")
            .assertContainsToken("that")
            .assertContainsToken("them")
            .assertSize(3)
    }

    private fun Set<Token>.assertContainsToken(token: Token): Set<Token> {
        assertTrue(contains(token), "stop-list should contain token $token")
        return this
    }

    private fun Set<Token>.assertDoesNotContainToken(token: Token): Set<Token> {
        assertFalse(contains(token), "stop-list should not contain token $token")
        return this
    }

    private fun Set<Token>.assertSize(expectedSize: Int): Set<Token> {
        assertEquals(expectedSize, size)
        return this
    }

    private fun createInputStreamWithLines(vararg lines: String): InputStream {
        val stringBuilder = StringBuilder()
        for (line in lines)
            stringBuilder.append(line + System.lineSeparator())

        return stringBuilder.toString().byteInputStream()
    }
}