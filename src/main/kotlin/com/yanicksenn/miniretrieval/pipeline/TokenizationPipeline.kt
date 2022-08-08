package com.yanicksenn.miniretrieval.pipeline

import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.to.Token

object TokenizationPipeline {

    fun pipeline(textRaw: String): List<Token> {
        val text = SanitizationPipeline.pipeline(textRaw)
        val tokensRaw = PreTokenizationPipeline.pipeline(text)

        val result = IdentificationPipeline.pipeline(tokensRaw)
        return when(result) {
            is LanguageDeterminer.Nothing, LanguageDeterminer.Unsure -> tokensRaw
            is LanguageDeterminer.Match -> LocalizedPipeline.pipeline(result.language, text)
        }
    }
}