package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import java.io.File

class Application(
    private val documentsDirectory: File,
    private val tokenizer: ITokenizer) : Runnable {

    init {
        validateDocumentsDirectory()
    }

    override fun run() {
        documentsDirectory.walk()
            .filter { it.isFile }
            .associateWith { it.tokenizeFile() }
            .forEach { println("${it.key.absolutePath} - ${it.value.size} tokens") }

        // tokens by file will be used for indexing
    }

    private fun validateDocumentsDirectory() {
        val absolutePath = documentsDirectory.absolutePath
        require(documentsDirectory.exists()) { "$absolutePath does not exists" }
        require(documentsDirectory.isDirectory) { "$absolutePath is not a directory" }
    }

    private fun File.tokenizeFile() = tokenizer.tokenize(readText())
}