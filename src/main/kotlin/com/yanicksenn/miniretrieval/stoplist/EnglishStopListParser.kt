package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.utility.TokenFileParser

class EnglishStopListParser : TokenFileParser(
    javaClass.getResourceAsStream("/stoplists/english.txt")!!)