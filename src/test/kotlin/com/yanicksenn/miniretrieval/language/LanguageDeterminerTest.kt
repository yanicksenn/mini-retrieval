package com.yanicksenn.miniretrieval.language

import com.yanicksenn.miniretrieval.tokenizer.SimpleNormalizer
import com.yanicksenn.miniretrieval.tokenizer.SimpleTokenizer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LanguageDeterminerTest {

    private var tokenizer = SimpleTokenizer()
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

        val languageDeterminer = LanguageDeterminer(lexicons)
        languageDeterminer.readTokens(tokenizer.tokenize(text))

        assertEquals(LanguageDeterminer.Match(hashSetOf(Language.ENGLISH)), languageDeterminer.currentLanguage)
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

        val languageDeterminer = LanguageDeterminer(lexicons)
        languageDeterminer.readTokens(tokenizer.tokenize(text))

        assertEquals(LanguageDeterminer.Match(hashSetOf(Language.GERMAN)), languageDeterminer.currentLanguage)
    }

    @Test
    fun `ensure no language is determined when no tokens are read`() {
        val languageDeterminer = LanguageDeterminer(lexicons)
        assertEquals(LanguageDeterminer.Nothing, languageDeterminer.currentLanguage)
    }

    @Test
    fun `ensure assurrance is zero when no tokens are read`() {
        val languageDeterminer = LanguageDeterminer(lexicons)
        assertEquals(0.0, languageDeterminer.currentAssurrance)
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
    fun `ensure multiple languages are returned when there is no clear language`() {
        val languageDeterminer = LanguageDeterminer(lexicons)

        languageDeterminer.readToken("ich")
        languageDeterminer.readToken("du")

        languageDeterminer.readToken("i")
        languageDeterminer.readToken("you")

        assertEquals(LanguageDeterminer.Match(hashSetOf(Language.ENGLISH, Language.GERMAN)), languageDeterminer.currentLanguage)
    }
}