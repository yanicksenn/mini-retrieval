package com.yanicksenn.miniretrieval.stoplist

import java.io.InputStream

class StopListParser(
    private val stopListInputStream: InputStream
) {

    fun parse(): StopList {
        val tokens = stopListInputStream.bufferedReader()
            .lineSequence()
            .filterNot { it.startsWith("#") }
            .filterNot { it.isBlank() }
            .map { it.trim() }
            .toHashSet()

        return StopList(tokens)
    }
}