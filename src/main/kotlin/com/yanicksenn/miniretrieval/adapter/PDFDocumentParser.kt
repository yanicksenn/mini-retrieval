package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.adapter.pdfbox.PDFBoxDocumentParser
import com.yanicksenn.miniretrieval.to.Document
import java.io.File


object PDFDocumentParser : IDocumentParser {
    override fun parse(file: File): Sequence<Document> {
        return PDFBoxDocumentParser.parse(file)
    }
}