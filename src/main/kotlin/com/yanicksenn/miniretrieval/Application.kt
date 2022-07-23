package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import java.io.File

class Application(
    private val documentsDirectory: File,
    private val tokenizer: ITokenizer) : Runnable {

    private val documentIndex = HashMap<DocumentId, HashMap<String, Int>>()

    init {
        validateDocumentsDirectory()
    }

    override fun run() {
        println("Indexing ...")
        documentsDirectory.walk()
            .filter { it.isFile }
            .forEach { file ->
                println("  ${file.absolutePath}")
                addFileToDocumentIndex(file)
            }
    }

    private fun addFileToDocumentIndex(file: File) {
        val documentId = file.toDocumentId()
        file.tokenizeFile().forEach { token ->
            val tokens = documentIndex.getOrPut(documentId) { HashMap() }
            tokens[token] = tokens.getOrElse(token) { 0 } + 1
        }
    }

    private fun validateDocumentsDirectory() {
        val absolutePath = documentsDirectory.absolutePath
        require(documentsDirectory.exists()) { "$absolutePath does not exists" }
        require(documentsDirectory.isDirectory) { "$absolutePath is not a directory" }
    }

    private fun File.tokenizeFile() = tokenizer.tokenize(readText())
    private fun File.toDocumentId(): DocumentId = DocumentId(absolutePath)

    data class DocumentId(val path: String)
}