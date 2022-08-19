package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.to.DocumentId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path
import kotlin.test.assertEquals

class TXTDocumentParserTest {

    @TempDir
    lateinit var documentsRoot: Path

    @Test
    fun `should parse txt file`() {
        val file = copyTempResource("/com/yanicksenn/miniretrieval/adapter/example.txt")

        val documents = TXTDocumentParser.parse(file).toList()
        assertEquals(1, documents.size)
        documents.assertDocumentMatches(file.absolutePath, "\\s*Page 1\\s*".toRegex())
    }

    /**
     * Copy a resource to a temporary location to be able
     * fully interact with it.
     * @param resourceName Name of the resource
     */
    private fun copyTempResource(resourceName: String): File {
        val inputStream = javaClass.getResourceAsStream(resourceName)!!
        val fileName = resourceName.split("/").last()
        val file = documentsRoot.resolve(fileName).toFile()
        file.createNewFile()
        file.outputStream().use { inputStream.transferTo(it) }
        return file
    }

    private fun List<Document>.assertDocumentMatches(documentId: DocumentId, pattern: Regex) {
        kotlin.test.assertTrue(filter { it.id == documentId }.any { it.text.matches(pattern) })
    }
}