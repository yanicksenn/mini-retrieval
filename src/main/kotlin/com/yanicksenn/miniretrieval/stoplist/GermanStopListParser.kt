package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.stemmer.GermanStemmer
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.utility.TokenFileParser

class GermanStopListParser : TokenFileParser(
    javaClass.getResourceAsStream("/stoplists/german.txt")!!) {

    override fun manipulate(token: Token): Token {
        return GermanStemmer().stem(token)
    }
}