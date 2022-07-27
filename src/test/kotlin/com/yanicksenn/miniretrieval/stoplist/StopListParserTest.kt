package com.yanicksenn.miniretrieval.stoplist

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StopListParserTest {

    @TempDir
    lateinit var tempDir: File

    @Test
    fun `ensure parsing stop-list contains the correct tokens`() {
        val stopListFile = createStopListFile(
            "this",
            "that",
            "them")

        StopListParser("english", stopListFile).parse()
            .assertContainsToken("this")
            .assertContainsToken("that")
            .assertContainsToken("them")
            .assertSize(3)
    }

    @Test
    fun `ensure lines starting with # are ignored`() {
        val stopListFile = createStopListFile(
            "# this",
            "that",
            "# them")

        StopListParser("english", stopListFile).parse()
            .assertDoesNotContainToken("this")
            .assertContainsToken("that")
            .assertDoesNotContainToken("them")
            .assertSize(1)
    }

    @Test
    fun `ensure blank lines are ignored`() {
        val stopListFile = createStopListFile(
            "this",
            "",
            "   ",
            "them")

        StopListParser("english", stopListFile).parse()
            .assertContainsToken("this")
            .assertContainsToken("them")
            .assertSize(2)
    }

    @Test
    fun `ensure leading and trailing whitespaces are trimmed`() {
        val stopListFile = createStopListFile(
            "  this  ",
            "that ",
            " them")

        StopListParser("english", stopListFile).parse()
            .assertContainsToken("this")
            .assertContainsToken("that")
            .assertContainsToken("them")
            .assertSize(3)
    }

    @Test
    fun `ensure language in stop-list is adopted from parser`() {
        val stopListFile = createStopListFile()
        StopListParser("english", stopListFile).parse()
            .assertLanguage("english")
    }

    private fun StopList.assertContainsToken(token: String): StopList {
        assertTrue(contains(token), "stop-list should contain token $token")
        return this
    }

    private fun StopList.assertDoesNotContainToken(token: String): StopList {
        assertFalse(contains(token), "stop-list should not contain token $token")
        return this
    }

    private fun StopList.assertSize(expectedSize: Int): StopList {
        assertEquals(expectedSize, size)
        return this
    }

    private fun StopList.assertLanguage(expectedLanguage: String): StopList {
        assertEquals(expectedLanguage, language)
        return this
    }

    private fun createStopListFile(vararg lines: String): File {
        val stopListFile = File(tempDir, "stop-list.txt")
        stopListFile.createNewFile()

        lines.forEach { stopListFile.appendText(it + System.lineSeparator()) }

        return stopListFile
    }
}