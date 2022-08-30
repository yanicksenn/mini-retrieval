package com.yanicksenn.miniretrieval.modifier

import com.yanicksenn.miniretrieval.TFIDFIndex
import com.yanicksenn.miniretrieval.to.DocumentId
import kotlin.math.min

/**
 * Modifier that multiplies the current score with factor of its weight.
 */
class DocumentWeightModifier(private val index: TFIDFIndex, private val threshold: Int = 30) : IModifier {
    override fun modify(documentId: DocumentId, score: Double): Double {
        val weight = index.documentWeight[documentId] ?: 0
        val factor = min(weight, threshold).toDouble() / threshold
        return score * factor
    }
}