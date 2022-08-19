package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File


object PDFDocumentParser : IDocumentParser {
    override fun parse(file: File): Sequence<Document> {
        return sequence {
            val document = PDDocument.load(file)

            val documentIdPrefix = file.absolutePath
            for (i in 1 .. document.numberOfPages) {
                val reader = PDFTextStripper()
                reader.startPage = i
                reader.endPage = i

                val documentId = "$documentIdPrefix#$i"
                val text = reader.getText(document)
                yield(Document(documentId, text))
            }
        }
    }
}