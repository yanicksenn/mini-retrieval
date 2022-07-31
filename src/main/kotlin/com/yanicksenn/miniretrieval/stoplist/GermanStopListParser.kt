package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.stemmer.IStemmer

class GermanStopListParser(stemmer: IStemmer) : StemmedStopListParser(
    javaClass.getResourceAsStream("/stoplists/german.txt")!!, stemmer)