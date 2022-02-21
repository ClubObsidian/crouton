package com.clubobsidian.crouton.test

import com.clubobsidian.crouton.Crouton
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class TestCrouton {

    @Test
    fun testAsyncBlocking() {
        val crouton = Crouton()
        val ran = AtomicBoolean(false)
        var job = crouton.async(runnable = Runnable {
            ran.set(true)
        })

        while(job.isRunning()) {
            Thread.sleep(1)
        }

        assert(ran.get())
    }

    @Test
    fun testAsync() {
        val crouton = Crouton()
        val ran = AtomicBoolean(false)
        var wrapper = crouton.async(runnable = Runnable {
            ran.set(true)
        })

        while(wrapper.isRunning()) {}

        assert(ran.get())
    }

    @Test
    fun testAsyncDelayedBlocking() = runBlockingTest {
        val crouton = Crouton()
        val ran = AtomicBoolean(false)
        var wrapper = crouton.asyncDelayed(runnable = Runnable {
            ran.set(true)
        }, delay = 1)

        while(wrapper.isRunning()) {}

        assert(ran.get())
    }

    @Test
    fun testAsyncDelayed() {
        val crouton = Crouton()
        val ran = AtomicBoolean(false)
        var job = crouton.asyncDelayed(runnable = Runnable {
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
        val crouton = Crouton()
        val count = AtomicInteger(0)
        var wrapper = crouton.asyncRepeating(runnable = Runnable {
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
        val crouton = Crouton()
        val callable: Callable<Boolean> = Callable<Boolean> {
           true
        }

        val wrapper = crouton.await(callable)

        while(wrapper.isRunning()) {}

        assert(wrapper.getFuture()!!.get() as Boolean)
    }


    @Test
    fun testSleep() {
        val count = AtomicInteger(0)
        val crouton = Crouton()
        val wrapper = crouton.async(runnable = Runnable {
            count.incrementAndGet()
            crouton.sleep(1000)
            count.incrementAndGet()
        })

        crouton.async(runnable = Runnable {
            crouton.sleep(500)
            count.incrementAndGet()
        })

        while(wrapper.isRunning()) {}

        assert(count.get() == 3)
    }

    @Test
    fun testSleepBlocking() {
        val count = AtomicInteger(0);
        val crouton = Crouton()
        val wrapper = crouton.async(runnable = Runnable {
            crouton.sleep(1000)
            count.incrementAndGet()
        })

        crouton.async(runnable = Runnable {
            crouton.sleep(100)
            wrapper.stop()
        })

        while(wrapper.isRunning()) {
            Thread.sleep(1)
        }

        println("count ${count.get()}")
        assert(count.get() == 0)
    }
}