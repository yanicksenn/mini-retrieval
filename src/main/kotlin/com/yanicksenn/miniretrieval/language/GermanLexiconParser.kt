package com.yanicksenn.miniretrieval.language

import com.yanicksenn.miniretrieval.utility.TokenFileParser

object GermanLexiconParser : TokenFileParser(
    javaClass.getResourceAsStream("/lexicons/german.txt")!!)