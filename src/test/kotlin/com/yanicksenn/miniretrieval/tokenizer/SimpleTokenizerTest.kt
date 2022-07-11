package com.yanicksenn.miniretrieval.tokenizer

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimpleTokenizerTest {

    private lateinit var tokenizer: ITokenizer

    @BeforeEach
    fun beforeEach() {
        tokenizer = SimpleTokenizer()
    }

    @Test
    fun `empty text should return no tokens`() {
        val tokens = tokenizer.tokenize("")
        assertTrue(tokens.isEmpty())
    }

    @Test
    fun `blank text should return no tokens`() {
        val tokens = tokenizer.tokenize("    ")
        assertTrue(tokens.isEmpty())
    }

    @Test
    fun `single word in text should return single token`() {
        val tokens = tokenizer.tokenize("green")
        assertTrue(tokens.size == 1)
        assertEquals("green", tokens.first())
    }

    @Test
    fun `leading white-spaces are not part of a token`() {
        val tokens = tokenizer.tokenize("  green")
        assertTrue(tokens.size == 1)
        assertEquals("green", tokens.first())
    }

    @Test
    fun `trailing white-spaces are not part of a token`() {
        val tokens = tokenizer.tokenize("green   ")
        assertTrue(tokens.size == 1)
        assertEquals("green", tokens.first())
    }

    @Test
    fun `text with multiple words is correctly tokenized`() {
        val tokens = tokenizer.tokenize("hi my name is Yanick")
        assertTrue(tokens.size == 5)
        assertTrue(tokens.containsAll(listOf("hi", "my", "name", "is", "yanick")))
    }

    @Test
    fun `comma is not tokenized`() {
        val tokens = tokenizer.tokenize("Hello, World")
        assertTrue(tokens.size == 2)
        assertTrue(tokens.containsAll(listOf("hello", "world")))
    }

    @Test
    fun `punctuation mark is not tokenized`() {
        val tokens = tokenizer.tokenize("I am at home.")
        assertTrue(tokens.size == 4)
        assertTrue(tokens.containsAll(listOf("i", "am", "at", "home")))
    }
}