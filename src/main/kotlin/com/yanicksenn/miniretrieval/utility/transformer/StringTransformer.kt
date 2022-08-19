package com.yanicksenn.miniretrieval.utility.transformer


/**
 * Removes all xml tags from this string.
 */
fun String.removeXMLTags(): String {
    val buffer = StringBuilder()
    var inTag = false

    for (char in this) {
        if (char == '<') {
            inTag = true
            continue
        }

        if (char == '>') {
            inTag = false
            buffer.append(' ')
            continue
        }

        if (inTag)
            continue

        buffer.append(char)
    }

    return buffer.toString()
}