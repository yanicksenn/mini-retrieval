package com.yanicksenn.miniretrieval.language

import com.yanicksenn.miniretrieval.utility.TokenFileParser

class GermanLexiconParser : TokenFileParser(
    javaClass.getResourceAsStream("/lexicons/german.txt")!!)