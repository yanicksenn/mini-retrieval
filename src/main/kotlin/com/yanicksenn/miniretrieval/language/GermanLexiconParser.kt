package com.yanicksenn.miniretrieval.language

class GermanLexiconParser : LexiconParser(
    javaClass.getResourceAsStream("/lexicons/german.txt")!!)