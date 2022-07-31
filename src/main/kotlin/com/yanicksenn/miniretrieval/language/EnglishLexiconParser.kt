package com.yanicksenn.miniretrieval.language

import com.yanicksenn.miniretrieval.utility.TokenFileParser

class EnglishLexiconParser : TokenFileParser(
    javaClass.getResourceAsStream("/lexicons/english.txt")!!)