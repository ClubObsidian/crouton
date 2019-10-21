package com.clubobsidian.crouton.test

import com.clubobsidian.crouton.Crouton
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class TestCrouton {

    private val mainThreadSurrogate = newSingleThreadContext("Main thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        this.mainThreadSurrogate.close()
    }

    @Test
    fun testAsyncBlocking() = runBlockingTest {
        val crouton = Crouton()
        val ran = AtomicBoolean(false)
        crouton.async(runnable = Runnable {
            ran.set(true)
        })

        crouton.async(runnable = Runnable {
            assert(ran.get())
        })
    }

    @Test
    fun testAsync() {
        val crouton = Crouton()
        val ran = AtomicBoolean(false)
        var wrapper = crouton.async(runnable = Runnable {
            ran.set(true)
        })

        while(!ran.get());

        assert(ran.get())
    }

    @Test
    fun testAsyncDelayedBlocking() = runBlockingTest {
        val crouton = Crouton()
        val ran = AtomicBoolean(false)
        var wrapper = crouton.asyncDelayed(runnable = Runnable {
            ran.set(true)
        }, delay = 1)

        while(wrapper.isRunning());

        assert(ran.get())
    }

    @Test
    fun testAsyncDelayed() {
        val crouton = Crouton()
        val ran = AtomicBoolean(false)
        crouton.asyncDelayed(runnable = Runnable {
           ran.set(true)
        }, delay = 1)

        assert(!ran.get())
    }

    @Test
    fun testAsyncRepeatingBlocking() = runBlockingTest {
        val crouton = Crouton()
        val count = AtomicInteger(0)
        var wrapper = crouton.asyncRepeating(runnable = Runnable {
            if(count.get() != 10) {
                count.incrementAndGet()
            }
        }, initialDelay = 1, repeatingDelay = 1)

        while(wrapper.isRunning()) {
            if(count.get() >= 10) {
                break
            }
        }

        assert(count.get() == 10)
    }
}