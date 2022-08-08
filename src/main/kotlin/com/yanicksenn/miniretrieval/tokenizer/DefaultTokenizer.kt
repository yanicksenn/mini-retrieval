package com.yanicksenn.miniretrieval.tokenizer

import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.opennlp.WhitespaceTokenizerOpennlp

object DefaultTokenizer : ITokenizer {
    private val tokenizer = WhitespaceTokenizerOpennlp()

    override fun tokenize(text: String): List<Token> {
        return tokenizer.tokenize(text)
    }
}