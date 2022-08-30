package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.modifier.IModifier
import com.yanicksenn.miniretrieval.to.DocumentId
import com.yanicksenn.miniretrieval.to.DocumentResult
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Retrieval status value calculator for tf-idf.
 */
class TFIDFRSV(
    private val tfidfIndex: TFIDFIndex,
    private val tokenizer: ITokenizer,
    private val modifier: IModifier) {

    private val tokenIndex = tfidfIndex.tokenIndex
    private val documentIndex = tfidfIndex.documentIndex
    
    fun query(rawQuery: String): List<DocumentResult> {
        val queryIndex = StringFrequency()
        val tokens = tokenizer.tokenize(rawQuery)
        tokens.forEach { queryIndex.add(it) }
        
        val accumulator = HashMap<String, Double>()
        for (token in queryIndex.keys) {
            if (!tokenIndex.contains(token))
                continue

            val b = queryIndex.b(token)
            val documents = tokenIndex[token]?.keys ?: emptySet()
            for (document in documents) {
                val a = documentIndex.a(document, token)
                var score = accumulator[document] ?: 0.0
                score += a * b
                accumulator[document] = score
            }
        }

        val qNorm = queryIndex.qNorm()
        for ((document, score) in accumulator) {
            val dNorm = documentIndex.dNorm(document)
            val sNorm = score / (dNorm * qNorm)
            accumulator[document] = modifier.modify(document, sNorm)
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


    private fun Map<String, Map<String, Int>>.dNorm(documentId: DocumentId): Double {
        val tokens = getOrElse(documentId) { emptyMap() }
        return sqrt(tokens.keys.sumOf { a(documentId, it).pow(2) })
    }

    private fun Map<String, Map<String, Int>>.a(documentId: DocumentId, token: Token): Double {
        return tf(documentId, token) * idf(token)
    }


    private fun Map<String, Int>.qNorm(): Double {
        return sqrt(keys.sumOf { b(it).pow(2) })
    }

    private fun Map<String, Int>.b(token: Token): Double {
        return tf(token) * idf(token)
    }

    private fun idf(token: Token): Double {
        return tfidfIndex.idf(token)
    }
}