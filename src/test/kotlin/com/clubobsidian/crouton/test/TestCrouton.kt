package com.clubobsidian.crouton.test

import com.clubobsidian.crouton.Crouton
import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class TestCrouton {

    @Test
    fun testAsyncBlocking() {
        val ran = AtomicBoolean(false)
        val job = Crouton.async(runnable = Runnable {
            ran.set(true)
        })

        while(job.isRunning()) {
            Thread.sleep(1)
        }

        assertTrue(ran.get())
    }

    @Test
    fun testAsync() {
        val ran = AtomicBoolean(false)
        val wrapper = Crouton.async(runnable = Runnable {
            ran.set(true)
        })

        while(wrapper.isRunning()) {
            Thread.sleep(1)
        }

        assertTrue(ran.get())
    }

    @Test
    fun testAsyncDelayedBlocking() {
        val ran = AtomicBoolean(false)
        val wrapper = Crouton.asyncDelayed(runnable = Runnable {
            ran.set(true)
        }, delay = 1)

        while(wrapper.isRunning()) {
            Thread.sleep(1)
        }

        assertTrue(ran.get())
    }

    @Test
    fun testAsyncDelayed() {
        val ran = AtomicBoolean(false)
        val job = Crouton.asyncDelayed(runnable = Runnable {
            ran.set(true)
        }, delay = 1)

        while(job.isRunning()) {
            Thread.sleep(1)
        }

        assertTrue(ran.get())
    }

    private fun increment(currentCount: AtomicInteger, countTo: Int) {
        currentCount.updateAndGet { current ->
            var incremented = current
            if (incremented < countTo) {
                incremented += 1
            }
            incremented
        }
    }

    @Test
    fun testAsyncRepeating() {
        val count = AtomicInteger(0)
        val countTo = 10

        val wrapper1 = Crouton.asyncRepeating(runnable = Runnable {
            increment(count, countTo)
        }, initialDelay = 1, repeatingDelay = 1)

        val wrapper2 = Crouton.asyncRepeating(runnable = Runnable {
            increment(count, countTo)
        }, initialDelay = 1, repeatingDelay = 1)

        while(wrapper1.isRunning() && wrapper2.isRunning()) {
            if(count.get() >= 10) {
                Thread.sleep(1)
                wrapper1.stop()
                wrapper2.stop()
            }
        }

        val getCount = count.get()
        println("Count: $getCount")
        assertEquals(countTo, getCount);
    }

    @Test
    fun testAwait() {
        val callable: Callable<Boolean> = Callable<Boolean> {
            true
        }

        val wrapper = Crouton.await(callable)

        while(wrapper.isRunning()) {
            Thread.sleep(1)
        }
        assertTrue(wrapper.get() as Boolean)
    }
}