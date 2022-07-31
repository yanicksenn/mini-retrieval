package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.stemmer.IStemmer

class EnglishStopListParser(stemmer: IStemmer) : StemmedStopListParser(
    javaClass.getResourceAsStream("/stoplists/english.txt")!!, stemmer)