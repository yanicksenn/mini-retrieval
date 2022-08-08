package com.yanicksenn.miniretrieval.tokenizer

import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.opennlp.EnglishTokenizerOpennlp

object EnglishTokenizer : ITokenizer {
    private val tokenizer = EnglishTokenizerOpennlp()

    override fun tokenize(text: String): List<Token> {
        return tokenizer.tokenize(text)
    }
}