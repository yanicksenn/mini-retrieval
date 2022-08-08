package com.yanicksenn.miniretrieval.tokenizer.opennlp

import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import opennlp.tools.tokenize.TokenizerME
import opennlp.tools.tokenize.TokenizerModel

class EnglishTokenizerOpennlp : ITokenizer {
    private val model = TokenizerModel(javaClass.getResourceAsStream("/tokenizer/opennlp/en-token.bin"))
    private val tokenizer = TokenizerME(model)

    override fun tokenize(text: String): List<Token> {
        return tokenizer.tokenize(text).asList()
    }
}