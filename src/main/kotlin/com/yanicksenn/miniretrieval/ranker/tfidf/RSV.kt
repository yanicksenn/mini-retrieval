package com.yanicksenn.miniretrieval.ranker.tfidf

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.ranker.RankerResult
import com.yanicksenn.miniretrieval.to.DocumentId
import com.yanicksenn.miniretrieval.to.Token
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Retrieval status value calculator for tf-idf.
 */
class RSV(
    private val documentIndex: StringFrequencyByKey,
    private val tokenIndex: StringFrequencyByKey,
    private val queryFrequency: StringFrequency
) {

    fun query(): List<RankerResult> {
        val accumulator = HashMap<String, Double>()
        for (token in queryFrequency.keys) {
            if (!tokenIndex.contains(token))
                continue

            val b = b(token)
            val documentsWithToken = tokenIndex[token]
            for (document in documentsWithToken.keys) {
                val a = a(document, token)
                var score = accumulator.getOrDefault(document, 0.0)
                score += a * b
                accumulator[document] = score
            }
        }

        val qNorm = qNorm()
        for ((document, score) in accumulator) {
            val dNorm = dNorm(document)
            val sNorm = score / (dNorm * qNorm)
            val documentWeight = minOf(documentIndex[document].entries.sumOf { it.value } / 30.0, 1.0)
            accumulator[document] = sNorm * documentWeight
        }

        return accumulator.toList()
            .sortedByDescending { it.second }
            .map { RankerResult(it.first, it.second) }
    }

    private fun StringFrequencyByKey.tf(documentId: DocumentId, token: Token): Double {
        return log10(1.0 + this[documentId][token])
    }

    private fun StringFrequency.tf(token: Token): Double {
        return log10(1.0 + this[token])
    }

    private fun idf(token: Token): Double {
        return log10((documentIndex.size + 1.0) / (tokenIndex[token].size + 1.0))
    }


    private fun dNorm(documentId: DocumentId): Double {
        val tokens = documentIndex.getOrElse(documentId) { StringFrequency() }
        return sqrt(tokens.keys.sumOf { a(documentId, it).pow(2) })
    }

    private fun a(documentId: DocumentId, token: Token): Double {
        return documentIndex.tf(documentId, token) * idf(token)
    }


    private fun qNorm(): Double {
        return sqrt(queryFrequency.keys.sumOf { b(it).pow(2) })
    }

    private fun b(token: Token): Double {
        return queryFrequency.tf(token) * idf(token)
    }
}