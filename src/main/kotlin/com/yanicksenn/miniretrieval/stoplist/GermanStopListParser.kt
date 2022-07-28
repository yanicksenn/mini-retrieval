package com.yanicksenn.miniretrieval.stoplist

class GermanStopListParser : StopListParser(
    javaClass.getResourceAsStream("/stopwords/german.txt")!!)