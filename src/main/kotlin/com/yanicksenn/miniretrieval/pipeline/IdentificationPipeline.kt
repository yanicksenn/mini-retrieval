package com.yanicksenn.miniretrieval.pipeline

import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.to.Token

object IdentificationPipeline {
    private val lexicons = LexiconsBuilder.build()

    fun pipeline(tokens: List<Token>): LanguageDeterminer.Result {
        val languageDeterminer = LanguageDeterminer(lexicons)
        tokens.forEach { token ->
            languageDeterminer.readToken(token)
        }

        return languageDeterminer.currentLanguage
    }
}