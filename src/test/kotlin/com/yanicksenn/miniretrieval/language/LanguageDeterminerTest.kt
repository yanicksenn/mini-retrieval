package com.yanicksenn.miniretrieval.language

import com.yanicksenn.miniretrieval.tokenizer.WhitespaceTokenizer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LanguageDeterminerTest {

    private val tokenizer = WhitespaceTokenizer()
    private var lexicons = LexiconsBuilder.build()

    @Test
    fun `ensure text is found to be english`() {
        val text = """
            Alice was beginning to get very tired of sitting by her 
            sister on the bank, and of having nothing to do: once 
            or twice she had peeped into the book her sister was 
            reading, but it had no pictures or conversations in it, 
            'and what is the use of a book,' thought Alice 'without 
            pictures or conversation?'
        """

        val tokens = tokenizer.tokenize(text)
        val languageDeterminer = LanguageDeterminer(lexicons)
        tokens.forEach { languageDeterminer.readToken(it.lowercase()) }

        assertEquals(LanguageDeterminer.Match(Language.ENGLISH), languageDeterminer.currentLanguage)
    }

    @Test
    fun `ensure text is found to be german`() {
        val text = """
            Alice fing an sich zu langweilen; sie saß schon lange 
            bei ihrer Schwester am Ufer und hatte nichts zu thun. 
            Das Buch, das ihre Schwester las, gefiel ihr nicht; denn 
            es waren weder Bilder noch Gespräche darin. „Und 
            was nützen Bücher,“ dachte Alice, „ohne Bilder und 
            Gespräche?“
        """

        val tokens = tokenizer.tokenize(text)
        val languageDeterminer = LanguageDeterminer(lexicons)
        tokens.forEach { languageDeterminer.readToken(it.lowercase()) }

        assertEquals(LanguageDeterminer.Match(Language.GERMAN), languageDeterminer.currentLanguage)
    }

    @Test
    fun `ensure no language is determined when no tokens are read`() {
        val languageDeterminer = LanguageDeterminer(lexicons)
        assertEquals(LanguageDeterminer.Nothing, languageDeterminer.currentLanguage)
    }

    @Test
    fun `ensure assurance is zero when no tokens are read`() {
        val languageDeterminer = LanguageDeterminer(lexicons)
        assertEquals(0.0, languageDeterminer.currentAssurance)
    }

    @Test
    fun `ensure unknown words to lexicon are not matched to a language`() {
        val languageDeterminer = LanguageDeterminer(emptyMap())
        languageDeterminer.readToken("hello")
        languageDeterminer.readToken("weather")
        languageDeterminer.readToken("superman")

        assertEquals(LanguageDeterminer.Nothing, languageDeterminer.currentLanguage)
    }

    @Test
    fun `ensure nothing is returned when language is not clearly definable`() {
        val languageDeterminer = LanguageDeterminer(lexicons)

        languageDeterminer.readToken("ich")
        languageDeterminer.readToken("du")

        languageDeterminer.readToken("i")
        languageDeterminer.readToken("you")

        assertEquals(LanguageDeterminer.Nothing, languageDeterminer.currentLanguage)
    }
}