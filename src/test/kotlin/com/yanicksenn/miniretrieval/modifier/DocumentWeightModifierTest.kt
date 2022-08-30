package com.yanicksenn.miniretrieval.modifier

import com.yanicksenn.miniretrieval.TFIDFIndex
import com.yanicksenn.miniretrieval.TFIDFTokenizer
import com.yanicksenn.miniretrieval.to.Document
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import kotlin.test.assertEquals


class DocumentWeightModifierTest {
    private val tokenizer = TFIDFTokenizer
    private val index = TFIDFIndex(tokenizer)
    private val modifier = DocumentWeightModifier(index, 30)

    @ParameterizedTest
    @ArgumentsSource(DocumentWeightTestCaseArgumentsProvider::class)
    fun `should calculate the document weight`(textCase: TestCase) {
        index.add(Document("doc1", createTextWithWords(textCase.amountOfWords)))
        assertEquals(textCase.score, modifier.modify("doc1", 1.0))
    }

    private fun createTextWithWords(amountOfWords: Int): String {
        val word = "Word"
        return (1..amountOfWords)
            .joinToString(" ") { word + it }
    }

    class DocumentWeightTestCaseArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? {
            return Stream.of(
                Arguments.of(TestCase(45, 1.0)),
                Arguments.of(TestCase(30, 1.0)),
                Arguments.of(TestCase(15, 0.5)),
                Arguments.of(TestCase(0, 0.0)),
            )
        }
    }

    data class TestCase(
        val amountOfWords: Int,
        val score: Double)
}