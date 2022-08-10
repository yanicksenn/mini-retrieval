package com.yanicksenn.miniretrieval.utility

inline fun <T> Sequence<T>.forEachAnd(action: (T) -> Unit): Sequence<T> {
    for (element in this) action(element)
    return this
}