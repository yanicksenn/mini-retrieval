package com.yanicksenn.miniretrieval.parser.adapter

import com.yanicksenn.miniretrieval.parser.IDocumentParser
import com.yanicksenn.miniretrieval.parser.pdfbox.PDFBoxDocumentParser
import com.yanicksenn.miniretrieval.to.Document
import java.io.File


object PDFDocumentParser : IDocumentParser {
    override fun parse(file: File): Sequence<Document> {
        return PDFBoxDocumentParser.parse(file)
    }
}