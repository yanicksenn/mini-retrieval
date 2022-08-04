package com.yanicksenn.miniretrieval.language

import com.yanicksenn.miniretrieval.utility.TokenFileParser

object EnglishLexiconParser : TokenFileParser(
    javaClass.getResourceAsStream("/lexicons/english.txt")!!)