package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertTrue

class ApplicationTest {

    @TempDir
    lateinit var root: File

    @Test
    fun `application aborts when documents directory does not exist`() {
        val documentDirectory = File(root, "documents")
        val ex = assertThrows<IllegalArgumentException> { initApplication(documentDirectory) }
        assertTrue(ex.message!!.contains(documentDirectory.absolutePath))
    }

    @Test
    fun `application aborts when documents directory is not a directory`() {
        val documentDirectory = File(root, "documents")
        documentDirectory.createNewFile()
        val ex = assertThrows<IllegalArgumentException> { initApplication(documentDirectory) }
        assertTrue(ex.message!!.contains(documentDirectory.absolutePath))
    }

    @Test
    fun `application continues when documents directory is valid`() {
        val documentDirectory = File(root, "documents")
        documentDirectory.mkdir()
        assertDoesNotThrow { initApplication(documentDirectory) }
    }

    private fun initApplication(documentDirectory: File): Application {
        return Application(documentDirectory, DummyTokenizer())
    }

    private class DummyTokenizer : ITokenizer {
        override fun tokenize(text: String): List<String> {
            return emptyList()
        }
    }
}