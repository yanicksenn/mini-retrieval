package com.yanicksenn.miniretrieval.stoplist

class EnglishStopListParser : StopListParser(
    javaClass.getResourceAsStream("/stopwords/english.txt")!!)