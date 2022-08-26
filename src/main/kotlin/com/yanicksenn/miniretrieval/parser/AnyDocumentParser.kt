package com.yanicksenn.miniretrieval.parser

import com.yanicksenn.miniretrieval.parser.adapter.PDFDocumentParser
import com.yanicksenn.miniretrieval.parser.adapter.PPTDocumentParser
import com.yanicksenn.miniretrieval.parser.adapter.TXTDocumentParser
import com.yanicksenn.miniretrieval.to.Document
import java.io.File

object AnyDocumentParser : IDocumentParser {
    override fun parse(file: File): Sequence<Document> {
        return when (file.extension.lowercase()) {
            "pdf" -> PDFDocumentParser.parse(file)
            "pptx", "ppt" -> PPTDocumentParser.parse(file)
            "txt" -> TXTDocumentParser.parse(file)
            else -> emptySequence()
        }
    }
}