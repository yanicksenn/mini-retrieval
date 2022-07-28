package com.yanicksenn.miniretrieval.tokenizer

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class SimpleNormalizerTest {

    private val normalizer = SimpleNormalizer()

    @ParameterizedTest
    @CsvSource(
        "café,      cafe",
        "cortège,   cortege",
        "naïve,     naive",
        "entrepôt,  entrepot",
        "façade,    facade",
        "jalapeño,  jalapeno",
    )
    fun `ensure that accents and diacritics are removed from a token`(input: String, expectedToken: String) {
        assertEquals(expectedToken, normalizer.normalize(input))
    }
}