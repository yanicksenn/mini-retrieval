package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import java.io.File

/**
 * API for a document parser.
 */
interface IDocumentParser {

    /**
     * Parses the given file and returns the
     * resulting sequence of documents.
     * @param file File
     */
    fun parse(file: File): Sequence<Document>
}