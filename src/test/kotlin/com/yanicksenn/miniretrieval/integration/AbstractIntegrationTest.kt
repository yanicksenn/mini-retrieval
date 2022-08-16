package com.yanicksenn.miniretrieval.integration

import com.yanicksenn.miniretrieval.main
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream
import kotlin.test.assertTrue


/**
 * Abstract API for integration tests.
 */
abstract class AbstractIntegrationTest {

    private lateinit var outStream: ByteArrayOutputStream
    private lateinit var errStream: ByteArrayOutputStream

    private lateinit var outOriginal: PrintStream
    private lateinit var errOriginal: PrintStream

    private var executed = false

    /**
     * Returns the current content in the output buffer.
     */
    protected val out: String
        get() = outStream.toString()

    /**
     * Returns the current content in the error buffer.
     */
    protected val err: String
        get() = errStream.toString()

    @BeforeEach
    protected fun setUpOutAndErrChannels() {
        outOriginal = System.out
        errOriginal = System.err

        outStream = ByteArrayOutputStream()
        errStream = ByteArrayOutputStream()

        System.setOut(TeePrintStream(outStream, outOriginal))
        System.setErr(TeePrintStream(errStream, errOriginal))
    }

    @AfterEach
    protected fun tearDownOutAndErrChannels() {
        outStream.close()
        errStream.close()

        System.setOut(outOriginal)
        System.setErr(errOriginal)
    }


    @BeforeEach
    protected fun assumeIntegrationTestIsNotYetExecuted() {
        executed = false
    }

    @AfterEach
    protected fun ensureIntegrationTestWasExecuted() {
        assertTrue(executed, "Integration test was not executed")
    }

    /**
     * Runs an integration test with the provided arguments and
     * determines if it was successful or a failure.
     */
    fun runIntegrationTest(args: String) {
        executed = true
        main(args.toArgs())
    }

    /**
     * Custom implementation of a teeing PrintStream.
     *
     * Source: https://gist.github.com/yanicksenn/1fcaacd2bff7a0ba6c09ec55d2eb6443
     */
    private class TeePrintStream(out: OutputStream, private vararg val delegates: PrintStream) : PrintStream(out) {
        override fun write(b: Int) {
            delegates.forEach { it.write(b) }
            super.write(b)
        }

        override fun write(x: ByteArray, o: Int, l: Int) {
            delegates.forEach { it.write(x, o, l) }
            super.write(x, o, l)
        }

        override fun flush() {
            delegates.forEach { it.flush() }
            super.flush()
        }

        override fun close() {
            delegates.forEach { it.close() }
            super.close()
        }
    }
}