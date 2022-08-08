package com.yanicksenn.miniretrieval.pipeline

import java.io.File

object ParsingPipeline {

    fun pipeline(file: File): Pair<String, String> {
        val document = file.absolutePath
        val text = file.readText()
        return Pair(document, text)
    }
}