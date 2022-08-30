package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.to.DocumentId
import com.yanicksenn.miniretrieval.to.Token

interface ITFIDFIndex {

    /**
     * Document index.
     */
    val documentIndex: Map<DocumentId, Map<Token, Int>>

    /**
     * Token index.
     */
    val tokenIndex: Map<Token, Map<DocumentId, Int>>

    /**
     * Document weight.
     */
    val documentWeight: Map<DocumentId, Int>
}
