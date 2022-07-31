package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.stemmer.IStemmer
import java.io.InputStream

open class StemmedStopListParser(stopListInputStream: InputStream, private val stemmer: IStemmer) : StopListParser(stopListInputStream) {
    override fun parse(): Set<String> {
        return super.parse().map { stemmer.stem(it) }.toHashSet()
    }
}