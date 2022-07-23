package com.yanicksenn.miniretrieval

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertTrue

class BootstrapIntegrationTest : AbstractIntegrationTest() {

    @TempDir
    lateinit var documentsDirectory: File

    @BeforeEach
    fun beforeEach() {
        createDocument("doc-1.txt", "Hello, World!")
        createDocument("doc-2.txt", """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
            Suspendisse efficitur sem ac nisl aliquet, eu suscipit nibh ullamcorper. 
            Proin tincidunt risus lacus, et porttitor nibh condimentum pretium. 
            Nulla sed varius tellus. 
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
            Nam euismod euismod tincidunt. 
            Mauris quis dignissim dui, ac porttitor mi. 
            Mauris mattis sed sem sit amet scelerisque. 
            Aliquam porta justo tellus, a blandit metus vulputate nec. 
            Mauris aliquam orci in orci elementum volutpat.
        """)
        createDocument("doc-3.txt", """
            While conventional search engines ranked results by counting how many 
            times the search terms appeared on the page, they theorized about a 
            better system that analyzed the relationships among websites.[28] 
            They called this algorithm PageRank; it determined a website's 
            relevance by the number of pages, and the importance of those pages 
            that linked back to the original site.[29][30] Page told his ideas to 
            Hassan, who began writing the code to implement Page's ideas.[24]
        """)
    }

    @Test
    fun `program iterates through files in documents directory`() {
        assertDoesNotThrow { runIntegrationTest(documentsDirectory.absolutePath) }

        out.lines()
            .assertDocumentWasVisited("doc-1.txt")
            .assertDocumentWasVisited("doc-2.txt")
            .assertDocumentWasVisited("doc-3.txt")
    }

    private fun List<String>.assertDocumentWasVisited(name: String): List<String> {
        assertTrue(any { it.contains(name) }, "file $name was not visited")
        return this
    }

    private fun createDocument(name: String, content: String) {
        val file = File(documentsDirectory, name)
        assertTrue(file.createNewFile(), "file $name already exists")
        file.writeText(content.trimIndent())
    }
}