package com.yanicksenn.miniretrieval.stoplist

import java.io.File

class StopListParser(
    private val language: String,
    private val stopListFile: File) {

    fun parse(): StopList {
        val tokens = stopListFile.bufferedReader()
            .lineSequence()
            .filterNot { it.startsWith("#") }
            .filterNot { it.isBlank() }
            .map { it.trim() }
            .toHashSet()

        return StopList(language, tokens)
    }
}