package com.yanicksenn.miniretrieval.language

class EnglishLexiconParser : LexiconParser(
    javaClass.getResourceAsStream("/stopwords/english.txt")!!)