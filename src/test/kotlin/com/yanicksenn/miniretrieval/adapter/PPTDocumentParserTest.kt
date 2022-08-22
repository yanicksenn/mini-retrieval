package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.to.DocumentId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PPTDocumentParserTest {

    @TempDir
    lateinit var documentRoot: Path

    @Test
    fun `should parse pptx file`() {
        val file = copyTempResource("/com/yanicksenn/miniretrieval/adapter/example.pptx")

        val documents = PPTDocumentParser.parse(file).toList()
        assertEquals(3, documents.size)
        documents.assertDocumentMatches("${file.absolutePath}#1", "\\s+Page 1\\s+".toRegex())
        documents.assertDocumentMatches("${file.absolutePath}#2", "\\s+Page 2\\s+".toRegex())
        documents.assertDocumentMatches("${file.absolutePath}#3", "\\s+Page 3\\s+".toRegex())
    }


    /**
     * Copy a resource to a temporary location to be able
     * fully interact with it.
     * @param resourceName Name of the resource
     */
    private fun copyTempResource(resourceName: String): File {
        val inputStream = javaClass.getResourceAsStream(resourceName)!!
        val fileName = resourceName.split("/").last()
        val file = documentRoot.resolve(fileName).toFile()
        file.createNewFile()
        file.outputStream().use { inputStream.transferTo(it) }
        return file
    }

    private fun List<Document>.assertDocumentMatches(documentId: DocumentId, pattern: Regex) {
        assertTrue(filter { it.id == documentId }.any { it.text.matches(pattern) })
    }
}