package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class TXTDocumentParserTest {

    @TempDir
    lateinit var documentsRoot: File

    @Test
    fun `should yield a single document`() {
        val file = "Hello, World!".writeInto("MyText.txt")
        val result = TXTDocumentParser.parse(file).toList()
        assertEquals(1, result.size)
    }

    @Test
    fun `should yield a document with the correct text`() {
        val file = "Hello, World!".writeInto("MyText.txt")
        val result = TXTDocumentParser.parse(file).first()
        assertEquals(Document(file.absolutePath, "Hello, World!"), result)
    }

    private fun String.writeInto(fileName: String): File {
        val file = File(documentsRoot, fileName)
        file.createNewFile()
        file.writeText(this)
        return file
    }
}