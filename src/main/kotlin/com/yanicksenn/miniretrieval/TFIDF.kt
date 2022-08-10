package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.pipeline.ParsingPipeline
import com.yanicksenn.miniretrieval.pipeline.TokenizationPipeline
import java.io.File

/**
 * TFIDF ranking model.
 */
class TFIDF(private val documentsRoot: File) {
    private val documentIndex = StringFrequencyByKey()
    private val tokenIndex = StringFrequencyByKey()

    /**
     * Rebuilds the document indexer based on the all
     * files within the documents root which have not
     * yet been indexed.
     */
    fun rebuildDocumentIndex(): TFIDF {
        documentsRoot.walk()
            .filter { it.isFile }
            .filterNot { documentIndex.contains(it.absolutePath) }
            .forEach { index(it) }

        return this
    }

    /**
     * Indexes the given file.
     * @param file File
     */
    fun index(file: File) {
        val (document, textRaw) = ParsingPipeline.pipeline(file)
        TokenizationPipeline.pipeline(textRaw)
            .forEach {
                documentIndex.add(document, it)
                tokenIndex.add(it, document)
            }
    }

    /**
     * Queries the relevant documents based on the
     * given raw query.
     * @param queryRaw Raw query
     */
    fun query(queryRaw: String): List<RSV.Result> {
        val queryFrequency = StringFrequency()
        TokenizationPipeline.pipeline(queryRaw)
            .forEach { queryFrequency.add(it) }

        return RSV(documentIndex, tokenIndex, queryFrequency).query()
    }
}