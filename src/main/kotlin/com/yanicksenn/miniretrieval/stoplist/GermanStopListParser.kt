package com.yanicksenn.miniretrieval.stoplist

class GermanStopListParser : StopListParser(
    javaClass.getResourceAsStream("/stoplists/german.txt")!!)