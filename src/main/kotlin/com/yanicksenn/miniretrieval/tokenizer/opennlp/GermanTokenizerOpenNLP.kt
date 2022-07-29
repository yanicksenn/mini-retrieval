package com.yanicksenn.miniretrieval.tokenizer.opennlp

import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import opennlp.tools.tokenize.TokenizerME
import opennlp.tools.tokenize.TokenizerModel

class GermanTokenizerOpenNLP : ITokenizer {
    private val model: TokenizerModel
    private val tokenizer: TokenizerME

    init {
        val inputStream = javaClass.getResourceAsStream("/tokenizer/opennlp/de-token.bin")
        model = TokenizerModel(inputStream)
        tokenizer = TokenizerME(model)
    }

    override fun tokenize(text: String): List<String> {
        return tokenizer.tokenize(text).toList()
    }
}