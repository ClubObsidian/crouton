package com.clubobsidian.crouton.test

import com.clubobsidian.crouton.Crouton
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class TestJobWrapper {

    @Test
    fun testGetJob() {
        val crouton = Crouton()
        val wrapper = crouton.async(runnable = Runnable {})
        assert(wrapper.getJob() != null)
    }

    @Test
    fun testStopWrapper() {
        val crouton = Crouton()
        val wrapper = crouton.async(runnable = Runnable {})
        wrapper.stop()
        assert(!wrapper.isRunning())
    }
}