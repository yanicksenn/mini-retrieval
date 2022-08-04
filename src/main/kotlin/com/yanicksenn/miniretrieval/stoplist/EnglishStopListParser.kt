package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.stemmer.EnglishStemmer
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.utility.TokenFileParser

class EnglishStopListParser : TokenFileParser(
    javaClass.getResourceAsStream("/stoplists/english.txt")!!) {

    override fun manipulate(token: Token): Token {
        return EnglishStemmer().stem(token)
    }
}