package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.DefaultTokenizer
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder

object TFIDFTokenizer : ITokenizer {
    private val lexicons = LexiconsBuilder.build()
    private val stemmers = StemmersBuilder.build()
    private val stopLists = StopListsBuilder.build()
    private val tokenizers = TokenizersBuilder.build()
    private val defaultTokenizer = DefaultTokenizer

    override fun tokenize(text: String): List<Token> {
        return tokenizeRawText(text)
    }

    private fun tokenizeRawText(textRaw: String): List<Token> {
        val text = sanitizeRawText(textRaw)
        val tokensRaw = tokenizeUnlocalizedText(text)

        return when(val result = localizeRawTokens(tokensRaw)) {
            is LanguageDeterminer.Nothing, LanguageDeterminer.Unsure -> tokensRaw
            is LanguageDeterminer.Match -> tokenizeLocalizedText(text, result.language)
        }
    }

    private fun sanitizeRawText(rawText: String): String {
        return rawText.lowercase()
    }

    private fun localizeRawTokens(rawTokens: List<Token>): LanguageDeterminer.Result {
        val languageDeterminer = LanguageDeterminer(lexicons)
        rawTokens.forEach { token ->
            languageDeterminer.readToken(token)
        }

        return languageDeterminer.currentLanguage
    }

    private fun tokenizeUnlocalizedText(text: String): List<Token> {
        return defaultTokenizer.tokenize(text)
    }

    private fun tokenizeLocalizedText(text: String, language: Language): List<Token> {
        val stemmer = stemmers[language]!!
        val stopList = stopLists[language]!!
        val tokenizer = tokenizers[language]!!

        return tokenizer.tokenize(text)
            .map { stemmer.stem(it) }
            .filterNot { stopList.contains(it) }
    }
}