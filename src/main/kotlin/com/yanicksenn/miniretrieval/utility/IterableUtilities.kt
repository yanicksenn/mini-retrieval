package com.yanicksenn.miniretrieval.utility

inline fun <T> Iterable<T>.forEachAnd(action: (T) -> Unit): Iterable<T> {
    for (element in this) action(element)
    return this
}