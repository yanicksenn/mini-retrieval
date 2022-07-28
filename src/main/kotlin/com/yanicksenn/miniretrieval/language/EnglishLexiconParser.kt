package com.yanicksenn.miniretrieval.language

class EnglishLexiconParser : LexiconParser(
    javaClass.getResourceAsStream("/lexicons/english.txt")!!)