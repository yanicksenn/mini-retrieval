package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.to.DocumentId
import com.yanicksenn.miniretrieval.to.DocumentResult
import com.yanicksenn.miniretrieval.to.Token
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Retrieval status value calculator for tf-idf.
 */
class TFIDFRSV(
    private val tfidfIndex: TFIDFIndex,
    private val queryFrequency: StringFrequency) {

    fun query(): List<DocumentResult> {
        val accumulator = HashMap<String, Double>()
        for (token in queryFrequency.keys) {
            if (!tfidfIndex.tokens.contains(token))
                continue

            val b = b(token)
            val documents = tfidfIndex.tokens[token]?.keys ?: emptySet()
            for (document in documents) {
                val a = a(document, token)
                var score = accumulator[document] ?: 0.0
                score += a * b
                accumulator[document] = score
            }
        }

        val qNorm = qNorm()
        for ((document, score) in accumulator) {
            val dNorm = dNorm(document)
            val sNorm = score / (dNorm * qNorm)
            accumulator[document] = sNorm
        }

        return accumulator.toList()
            .sortedByDescending { it.second }
            .map { DocumentResult(it.first, it.second) }
    }

    private fun Map<String, Map<String, Int>>.tf(documentId: DocumentId, token: Token): Double {
        return log10(1.0 + (this[documentId]?.get(token) ?: 0.0).toDouble())
    }

    private fun Map<String, Int>.tf(token: Token): Double {
        return log10(1.0 + (this[token] ?: 0.0).toDouble())
    }

    private fun idf(token: Token): Double {
        return log10((tfidfIndex.documents.size + 1.0) / ((tfidfIndex.tokens[token]?.size ?: 0.0).toDouble() + 1.0))
    }


    private fun dNorm(documentId: DocumentId): Double {
        val tokens = tfidfIndex.documents.getOrElse(documentId) { emptyMap() }
        return sqrt(tokens.keys.sumOf { a(documentId, it).pow(2) })
    }

    private fun a(documentId: DocumentId, token: Token): Double {
        return tfidfIndex.documents.tf(documentId, token) * idf(token)
    }


    private fun qNorm(): Double {
        return sqrt(queryFrequency.keys.sumOf { b(it).pow(2) })
    }

    private fun b(token: Token): Double {
        return queryFrequency.tf(token) * idf(token)
    }
}