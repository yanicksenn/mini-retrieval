package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.utility.StreamableZipEntry
import com.yanicksenn.miniretrieval.utility.asSequence
import com.yanicksenn.miniretrieval.utility.asZipInputStream
import com.yanicksenn.miniretrieval.utility.transformer.removeXMLTags
import java.io.File


object PPTDocumentParser : IDocumentParser {

    private const val slidePath = "ppt/slides"
    private val slideRegex = "$slidePath/slide\\d+.xml".toRegex()

    override fun parse(file: File): Sequence<Document> {
        val documentIdPrefix = file.absolutePath

        fun StreamableZipEntry.toDocument(): Document {
            val documentId = "$documentIdPrefix#${slideNumber}"
            val text = readText().removeXMLTags()
            return Document(documentId, text)
        }

        return sequence {
            file.asZipInputStream()
                .asSequence()
                .filter { it.isSlide() }
                .map { it.toDocument() }
                .forEach { yield(it) }
        }
    }

    private fun StreamableZipEntry.isSlide(): Boolean {
        return entry.name
            .lowercase()
            .matches(slideRegex)
    }

    private fun StreamableZipEntry.readText(): String {
        return inputStream.bufferedReader().readText()
    }

    private val StreamableZipEntry.slideNumber: String
        get() {
            val name = entry.name.lowercase()
            val startIndex = slidePath.length + 6 // +6 because the slides have a "/slide" prefix
            val endIndex = name.length - 4 // -4 because the slides have a ".xml" suffix

            // ppt/slides/slide1.xml -> 1
            // ppt/slides/slide27.xml -> 27
            return name.substring(startIndex, endIndex)
        }
}