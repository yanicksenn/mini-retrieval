package com.yanicksenn.miniretrieval

import java.io.File

class Application(private val documentsDirectory: File) : Runnable {

    init {
        validateDocumentsDirectory()
    }

    override fun run() {
        documentsDirectory.walk()
            .filter { it.isFile }
            .forEach { println(it.absolutePath) }
    }

    private fun validateDocumentsDirectory() {
        val absolutePath = documentsDirectory.absolutePath
        require(documentsDirectory.exists()) { "$absolutePath does not exists" }
        require(documentsDirectory.isDirectory) { "$absolutePath is not a directory" }
    }
}