package com.yanicksenn.miniretrieval.pipeline

object SanitizationPipeline {
    fun pipeline(text: String): String {
        return text.lowercase()
    }
}