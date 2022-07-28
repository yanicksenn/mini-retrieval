package com.yanicksenn.miniretrieval.stoplist

class EnglishStopListParser : StopListParser(
    javaClass.getResourceAsStream("/stoplists/english.txt")!!)