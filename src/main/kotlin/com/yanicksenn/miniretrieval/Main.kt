package com.yanicksenn.miniretrieval

import java.io.File

/**
 * Entry point for application.
 */
fun main(args: Array<String>) {
    require(args.size == 1) { "documents root path must be provided" }
    require(args[0].isNotBlank()) { "documents root must not be blank" }
    val documentsRoot = File(args[0])

    Application(documentsRoot).run()
}