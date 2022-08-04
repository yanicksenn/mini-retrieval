package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.Frequency
import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexer
import kotlin.math.log10
import kotlin.math.sqrt

class RSV(
    private val documentIndexer: TokenFrequencyIndexer,
    private val queryFrequency: Frequency<String>
) {

    fun query(): List<Result> {
        println("Accumulate documents ...")
        val accumulator = HashMap<String, Double>()
        for (token in queryFrequency.keys) {
            if (!documentIndexer.indexedTokens().contains(token))
                continue

            val b = b(token)
            val documentsWithToken = documentIndexer.findDocumentsByToken(token)
            for (document in documentsWithToken) {
                val a = a(document, token)
                var score = accumulator.getOrDefault(document, 0.0)
                score += a * b
                accumulator[document] = score
            }
        }

        println("Normalize document score ...")
        val qNorm = qNorm()
        for ((document, score) in accumulator) {
            val dNorm = dNorm(document)
            val sNorm = score / (dNorm * qNorm)
            accumulator[document] = sNorm
        }

        println("Sort results ...")
        return accumulator.toList()
            .sortedByDescending { it.second }
            .map { Result(it.first, it.second) }
    }

    private fun TokenFrequencyIndexer.tf(document: String, token: String): Double {
        return log10(1.0 + findFrequency(document, token))
    }

    private fun Frequency<String>.tf(token: String): Double {
        return log10(1.0 + getOrDefault(token, 0))
    }

    private fun TokenFrequencyIndexer.idf(token: String): Double {
        return log10((indexedDocuments().size + 1.0) / (findDocumentsByToken(token).size + 1.0))
    }

    private fun dNorm(document: String): Double {
        val tokens = documentIndexer.findTokensByDocument(document)
        return sqrt(tokens.sumOf { pow2(a(document, it)) })
    }

    private fun qNorm(): Double {
        return sqrt(queryFrequency.keys.sumOf { pow2(b(it)) })
    }

    private fun a(document: String, token: String): Double {
        return documentIndexer.tf(document, token) * documentIndexer.idf(token)
    }

    private fun b(token: String): Double {
        return queryFrequency.tf(token) * documentIndexer.idf(token)
    }

    private fun pow2(value: Double): Double {
        return value * value
    }

    data class Result(val document: String, val score: Double)
}