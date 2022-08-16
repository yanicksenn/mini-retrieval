package com.yanicksenn.miniretrieval.integration

fun String.toArgs(): Array<String> {
    val args = ArrayList<String>()
    val buffer = StringBuilder()
    var inQuotes = false

    val doubleQuote = '"'

    fun addAndClearIfNotEmpty() {
        if (buffer.isNotEmpty()) {
            args.add(buffer.toString())
            buffer.clear()
        }
    }

    for (char in this) {
        if (!inQuotes && char == doubleQuote) {
            inQuotes = true
            continue

        } else if (inQuotes && char == doubleQuote) {
            inQuotes = false
            addAndClearIfNotEmpty()
            continue
        }

        if (char.isWhitespace()) {
            if (inQuotes) {
                buffer.append(char)
            } else {
                addAndClearIfNotEmpty()
            }
        } else {
            buffer.append(char)
        }
    }

    addAndClearIfNotEmpty()

    return args.toTypedArray()
}