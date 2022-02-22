package com.clubobsidian.crouton.test

import com.clubobsidian.crouton.Crouton
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class TestCrouton {

    @Test
    fun testAsyncBlocking() {
        val ran = AtomicBoolean(false)
        var job = Crouton.async(runnable = Runnable {
            ran.set(true)
        })

        while(job.isRunning()) {
            Thread.sleep(1)
        }

        assert(ran.get())
    }

    @Test
    fun testAsync() {
        val ran = AtomicBoolean(false)
        var wrapper = Crouton.async(runnable = Runnable {
            ran.set(true)
        })

        while(wrapper.isRunning()) {}

        assert(ran.get())
    }

    @Test
    fun testAsyncDelayedBlocking() = runBlockingTest {
        val ran = AtomicBoolean(false)
        var wrapper = Crouton.asyncDelayed(runnable = Runnable {
            ran.set(true)
        }, delay = 1)

        while(wrapper.isRunning()) {}

        assert(ran.get())
    }

    @Test
    fun testAsyncDelayed() {
        val ran = AtomicBoolean(false)
        var job = Crouton.asyncDelayed(runnable = Runnable {
           ran.set(true)
        }, delay = 1)

        while(job.isRunning()) {}

        assert(ran.get())
    }

    private fun get(count: AtomicInteger) : Int {
        return count.get()
    }

    private fun increment(count: AtomicInteger) {
        count.incrementAndGet()
    }

    @Test
    fun testAsyncRepeating() {
        val count = AtomicInteger(0)
        var wrapper = Crouton.asyncRepeating(runnable = Runnable {
            runBlocking {
                if (get(count) < 10) {
                    increment(count)
                }
            }
        }, initialDelay = 1, repeatingDelay = 1)

        while (wrapper.isRunning()) {
            runBlocking {
                if (get(count) >= 10) {
                    wrapper.stop()
                }
            }
        }

        val getCount = count.get()
        println("Count: $getCount")
        assert(getCount == 10)
    }

    @Test
    fun testAwait() {
        val callable: Callable<Boolean> = Callable<Boolean> {
           true
        }

        val wrapper = Crouton.await(callable)

        while(wrapper.isRunning()) {}

        assert(wrapper.get() as Boolean)
    }
}