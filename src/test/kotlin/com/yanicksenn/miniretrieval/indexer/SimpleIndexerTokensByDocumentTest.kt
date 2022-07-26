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

class SimpleIndexerTokensByDocumentTest {

    @ParameterizedTest
    @ArgumentsSource(TokensByDocumentArgumentsProvider::class)
    fun `find tokens by document returns the correct amount of occurrences`(document: Document, expectedTokens: List<TokenAndExpectedOccurrences>) {
        val indexer = SimpleIndexer(SimpleTokenizer())
        val documentsRoot = File("src/test/resources/documents")

        assertDoesNotThrow {
            documentsRoot.walk()
                .filter { it.isFile }
                .forEach { indexer.addFileToIndex(it) }
        }

        val tokens = indexer.findTokensByDocument(document)
        for ((token, expectedOccurrences) in expectedTokens) {
            val actualOccurrences = tokens.getOrDefault(token, 0)
            assertEquals(actualOccurrences, expectedOccurrences,
                "index should contain token '$token' exactly $expectedOccurrences times but was $actualOccurrences")
        }
    }

    class TokensByDocumentArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(Document(File("src/test/resources/documents/doc-1.txt")), listOf(
                    TokenAndExpectedOccurrences("hello", 1),
                    TokenAndExpectedOccurrences("world", 1)
                )),
                Arguments.of(Document(File("src/test/resources/documents/doc-2.txt")), listOf(
                    TokenAndExpectedOccurrences("mauris", 3),
                    TokenAndExpectedOccurrences("ipsum", 2),
                    TokenAndExpectedOccurrences("blandit", 1)
                )),
                Arguments.of(Document(File("src/test/resources/documents/doc-3.txt")), listOf(
                    TokenAndExpectedOccurrences("page", 3),
                    TokenAndExpectedOccurrences("the", 7),
                )),
                Arguments.of(Document(File("src/test/resources/documents/doc-4.txt")), listOf(
                    TokenAndExpectedOccurrences("are", 3),
                    TokenAndExpectedOccurrences("jedi", 1),
                ))
            )
        }
    }
}
