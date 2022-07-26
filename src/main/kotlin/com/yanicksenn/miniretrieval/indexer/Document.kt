package com.yanicksenn.miniretrieval.indexer

import java.io.File

/**
 * A document encapsulating the absolute path of a File.
 */
data class Document(val path: String) {
    constructor(file: File) : this(file.absolutePath)
}