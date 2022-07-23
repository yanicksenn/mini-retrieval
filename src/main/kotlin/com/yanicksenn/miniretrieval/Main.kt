package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.tokenizer.SimpleTokenizer
import java.io.File

/**
 * Entry point for application.
 */
fun main(args: Array<String>) {
    require(args.size == 1) { "documents directory path must be provided" }
    require(args[0].isNotBlank()) { "documents directory must not be blank" }
    val documentsDirectory = File(args[0])

    Application(documentsDirectory, SimpleTokenizer()).run()
}