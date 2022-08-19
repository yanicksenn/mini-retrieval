package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import java.io.File

/**
 * Parses plain text files completely.
 */
object TXTDocumentParser : IDocumentParser {

    override fun parse(file: File): Sequence<Document> {
        return sequence {
            val documentId = file.absolutePath
            val text = file.readText()
            yield(Document(documentId, text))
        }
    }
}