package com.clubobsidian.crouton.test

import com.clubobsidian.crouton.Crouton
import org.junit.jupiter.api.Test

class TestJobWrapper {

    @Test
    fun testGetJob() {
        val wrapper = Crouton.async(runnable = Runnable {})
        assert(wrapper.getJob() != null)
    }

    @Test
    fun testStopWrapper() {
        val wrapper = Crouton.async(runnable = Runnable {})
        wrapper.stop()
        assert(!wrapper.isRunning())
    }
}