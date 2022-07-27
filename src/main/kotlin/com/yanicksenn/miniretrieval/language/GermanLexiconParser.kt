package com.yanicksenn.miniretrieval.language

class GermanLexiconParser : LexiconParser(
    javaClass.getResourceAsStream("/stopwords/german.txt")!!)