package com.yanicksenn.miniretrieval.modifier

import com.yanicksenn.miniretrieval.to.DocumentId

/**
 * Sequentially applies the provided modifiers in the
 * given order and calculates the new score.
 */
class SequentialModifier(private vararg val modifiers: IModifier) : IModifier {
    override fun modify(documentId: DocumentId, score: Double): Double {
        var newScore = score
        for (modifier in modifiers) {
            newScore = modifier.modify(documentId, newScore)
        }
        return newScore
    }
}