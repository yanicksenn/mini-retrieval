package com.yanicksenn.miniretrieval.utility.transformer

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class StringTransformerTest {

    @Test
    fun `should remove all xml tags`() {
        val xml = "<body>Pre<div><a href=\"https://google.com\">Mid</a></div>Post</body>"
        assertTrue(xml.removeXMLTags().matches("\\s+Pre\\s+Mid\\s+Post\\s+".toRegex()))
    }
}