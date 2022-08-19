package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.utility.transformer.removeXMLTags
import com.yanicksenn.miniretrieval.utility.unzipTo
import java.io.File
import java.nio.file.Files


object PPTXDocumentParser : IDocumentParser {

    override fun parse(file: File): Sequence<Document> {
        return sequence {
            val tmpPath = Files.createTempDirectory(file.name)
            file.unzipTo(tmpPath)

            val documentIdPrefix = file.absolutePath
            tmpPath.resolve("ppt").resolve("slides")
                .toFile()
                .listFiles { file -> file.isSlideXML }!!
                .reversed()
                .map { it.readText() }
                .map { it.removeXMLTags() }
                .withIndex()
                .map { (i, text) -> Document("$documentIdPrefix#$i", text) }
                .forEach { yield(it) }

            val tmpFile = tmpPath.toFile()
            tmpFile.deleteRecursively()
        }
    }

    private val File.isSlideXML: Boolean
        get() = name.lowercase().startsWith("slide") && extension.lowercase() == "xml"
}