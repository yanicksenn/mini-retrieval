package com.yanicksenn.miniretrieval.modifier

import com.yanicksenn.miniretrieval.to.DocumentId

interface IModifier {
    fun modify(documentId: DocumentId, score: Double): Double
}