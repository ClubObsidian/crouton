package com.clubobsidian.crouton.test

import com.clubobsidian.crouton.Crouton
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.FutureTask
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

        while(job.isRunning()) {}

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

    @Test
    fun testAsyncRepeating() {
        val crouton = Crouton()
        val count = AtomicInteger(0)
        var wrapper = crouton.asyncRepeating(runnable = Runnable {
            if(count.get() != 10) {
                count.incrementAndGet()
            }
        }, initialDelay = 1, repeatingDelay = 1)

        while(wrapper.isRunning()) {
            if(count.get() >= 10) {
                wrapper.stop()
                break
            }
        }

        val getCount = count.get()
        println("Count: $getCount")
        assert(getCount == 10)
    }

    //@Test
    fun testAwait() {
        val crouton = Crouton()
        val future: Future<Boolean> = FutureTask<Boolean>(Callable<Boolean>
        {
           true
        });
        val wrapper = crouton.await(future);

        while(wrapper.isRunning()) {}

        assert(wrapper.getFuture()!!.get() as Boolean);
    }
}