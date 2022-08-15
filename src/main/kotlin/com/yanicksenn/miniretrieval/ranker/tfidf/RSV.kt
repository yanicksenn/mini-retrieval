package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.to.Document
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

    fun query(): List<Result> {
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
            accumulator[document] = sNorm
        }

        return accumulator.toList()
            .sortedByDescending { it.second }
            .map { Result(it.first, it.second) }
    }

    private fun StringFrequencyByKey.tf(document: Document, token: Token): Double {
        return log10(1.0 + this[document][token])
    }

    private fun StringFrequency.tf(token: Token): Double {
        return log10(1.0 + this[token])
    }

    private fun idf(token: Token): Double {
        return log10((documentIndex.size + 1.0) / (tokenIndex[token].size + 1.0))
    }


    private fun dNorm(document: Document): Double {
        val tokens = documentIndex.getOrElse(document) { StringFrequency() }
        return sqrt(tokens.keys.sumOf { a(document, it).pow(2) })
    }

    private fun a(document: Document, token: Token): Double {
        return documentIndex.tf(document, token) * idf(token)
    }


    private fun qNorm(): Double {
        return sqrt(queryFrequency.keys.sumOf { b(it).pow(2) })
    }

    private fun b(token: Token): Double {
        return queryFrequency.tf(token) * idf(token)
    }

    /**
     * Retrieval result with document and score.
     */
    data class Result(
        override val document: String,
        val score: Double) : IResult
}