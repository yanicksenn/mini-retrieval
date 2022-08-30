package com.yanicksenn.miniretrieval.modifier

import com.yanicksenn.miniretrieval.to.DocumentId

class SequentialModifier(private vararg val modifiers: IModifier) : IModifier {
    override fun modify(documentId: DocumentId, score: Double): Double {
        var newScore = score
        for (modifier in modifiers) {
            newScore = modifier.modify(documentId, newScore)
        }
        return newScore
    }
}