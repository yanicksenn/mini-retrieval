package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.utility.TokenFileParser

class GermanStopListParser : TokenFileParser(
    javaClass.getResourceAsStream("/stoplists/german.txt")!!)