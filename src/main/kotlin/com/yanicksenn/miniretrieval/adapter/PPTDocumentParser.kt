package com.yanicksenn.miniretrieval.adapter

import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.utility.asSequence
import com.yanicksenn.miniretrieval.utility.transformer.removeXMLTags
import java.io.File
import java.nio.file.Files


object PPTDocumentParser : IDocumentParser {

    private const val slidePath = "ppt/slides"

    override fun parse(file: File): Sequence<Document> {
        return sequence {
            val tmpPath = Files.createTempDirectory(file.name)
            val tmpFile = tmpPath.toFile()

            file.asSequence()
                .filter { it.entry.name.contains(slidePath) }
                .forEach { it unzipTo tmpPath }

            val documentIdPrefix = file.absolutePath
            tmpPath.resolve(slidePath)
                .toFile()
                .listFiles { file -> file.isSlideXML }!!
                .map { it.nameWithoutExtension to it.readText().removeXMLTags() }
                .map { (name, text) -> Document("$documentIdPrefix#${name.slideNumber}", text) }
                .forEach { yield(it) }

            tmpFile.deleteRecursively()
        }
    }

    private val String.slideNumber: String
        get() = lowercase().replace("slide", "")

    private val File.isSlideXML: Boolean
        get() = name.lowercase().startsWith("slide") && extension.lowercase() == "xml"

}