package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.tokenizer.SimpleTokenizer
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.io.File
import java.util.stream.Stream
import kotlin.test.assertEquals

class SimpleIndexerDocumentsByTokenTest {

    @ParameterizedTest
    @ArgumentsSource(TokensByDocumentArgumentsProvider::class)
    fun `find documents by token returns the correct amount of occurrences`(token: String, expectedDocuments: List<DocumentAndExpectedOccurrences>) {
        val indexer = SimpleIndexer(SimpleTokenizer())
        val documentsRoot = File("src/test/resources/documents")

        assertDoesNotThrow {
            documentsRoot.walk()
                .filter { it.isFile }
                .forEach { indexer.addFileToIndex(it) }
        }

        val documents = indexer.findDocumentsByToken(token)
        for ((document, expectedOccurrences) in expectedDocuments) {
            val actualOccurrences = documents.getOrDefault(document, 0)
            assertEquals(actualOccurrences, expectedOccurrences,
                "index should contain document '$document' exactly $expectedOccurrences times but was $actualOccurrences")
        }
    }

    data class DocumentAndExpectedOccurrences(
        val document: Document,
        val expectedOccurrences: Int)

    class TokensByDocumentArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of("hello", listOf(
                    DocumentAndExpectedOccurrences(Document(File("src/test/resources/documents/doc-1.txt")), 1),
                )),
                Arguments.of("a", listOf(
                    DocumentAndExpectedOccurrences(Document(File("src/test/resources/documents/doc-2.txt")), 1),
                    DocumentAndExpectedOccurrences(Document(File("src/test/resources/documents/doc-3.txt")), 2),
                    DocumentAndExpectedOccurrences(Document(File("src/test/resources/documents/doc-4.txt")), 4),
                )),
                Arguments.of("of", listOf(
                    DocumentAndExpectedOccurrences(Document(File("src/test/resources/documents/doc-3.txt")), 2),
                    DocumentAndExpectedOccurrences(Document(File("src/test/resources/documents/doc-4.txt")), 6),
                )),
                Arguments.of("galaxy", listOf(
                    DocumentAndExpectedOccurrences(Document(File("src/test/resources/documents/doc-4.txt")), 2),
                ))
            )
        }
    }
}
