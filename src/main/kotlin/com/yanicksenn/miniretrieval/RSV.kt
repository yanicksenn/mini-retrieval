package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.TFIDFIndex
import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.to.Token
import kotlin.math.log10
import kotlin.math.sqrt

class RSV(
    private val documentIndex: TFIDFIndex,
    private val queryFrequency: StringFrequency
) {

    fun query(): List<Result> {
        val accumulator = HashMap<String, Double>()
        for (token in queryFrequency.keys) {
            if (!documentIndex.indexedTokens().contains(token))
                continue

            val b = b(token)
            val documentsWithToken = documentIndex.findDocumentsByToken(token)
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
            accumulator[document] = sNorm
        }

        return accumulator.toList()
            .sortedByDescending { it.second }
            .map { Result(it.first, it.second) }
    }

    private fun TFIDFIndex.tf(document: Document, token: Token): Double {
        return log10(1.0 + findTokensByDocument(document).getOrDefault(token, 0))
    }

    private fun StringFrequency.tf(token: Token): Double {
        return log10(1.0 + getOrDefault(token, 0))
    }

    private fun TFIDFIndex.idf(token: Token): Double {
        return log10((indexedDocuments().size + 1.0) / (findDocumentsByToken(token).size + 1.0))
    }

    private fun dNorm(document: Document): Double {
        val tokens = documentIndex.findTokensByDocument(document)
        return sqrt(tokens.keys.sumOf { pow2(a(document, it)) })
    }

    private fun qNorm(): Double {
        return sqrt(queryFrequency.keys.sumOf { pow2(b(it)) })
    }

    private fun a(document: Document, token: Token): Double {
        return documentIndex.tf(document, token) * documentIndex.idf(token)
    }

    private fun b(token: Token): Double {
        return queryFrequency.tf(token) * documentIndex.idf(token)
    }

    private fun pow2(value: Double): Double {
        return value * value
    }

    data class Result(val document: String, val score: Double)
}