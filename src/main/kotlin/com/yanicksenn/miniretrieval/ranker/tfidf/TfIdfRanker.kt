package com.yanicksenn.miniretrieval.ranker.tfidf

import com.yanicksenn.miniretrieval.IResult
import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.ranker.IRanker
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.DefaultTokenizer
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder
import java.io.File

/**
 * The ranker using tf-idf based ranking model.
 */
class TfIdfRanker(private val documentsRoot: File) : IRanker {
    private val lexicons = LexiconsBuilder.build()
    private val stemmers = StemmersBuilder.build()
    private val stopLists = StopListsBuilder.build()
    private val tokenizers = TokenizersBuilder.build()
    private val defaultTokenizer = DefaultTokenizer

    private val documentIndex = StringFrequencyByKey()
    private val tokenIndex = StringFrequencyByKey()

    override fun index(): Sequence<File> {
        return documentsRoot.walk()
            .filter { it.isFile }
            .filterNot { documentIndex.contains(it.absolutePath) }
            .map {
                indexFile(it)
                it
            }
    }

    override fun query(rawQuery: String): List<IResult> {
        val queryFrequency = StringFrequency()
        val tokens = tokenizeRawText(rawQuery)
        tokens.forEach { queryFrequency.add(it) }

        return RSV(documentIndex, tokenIndex, queryFrequency).query()
    }

    private fun indexFile(file: File) {
        val (document, rawText) = parseFile(file)
        val tokens = tokenizeRawText(rawText)
        tokens.forEach { token ->
            documentIndex.add(document, token)
            tokenIndex.add(token, document)
        }
    }

    private fun parseFile(file: File): Pair<String, String> {
        val document = file.absolutePath
        val rawText = file.readText()
        return Pair(document, rawText)
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