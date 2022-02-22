/*
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.clubobsidian.crouton

import com.clubobsidian.crouton.wrapper.JobWrapper
import com.clubobsidian.crouton.wrapper.JobWrapperCompletableFuture
import com.clubobsidian.crouton.wrapper.JobWrapperFuture
import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.concurrent.Callable

import java.util.concurrent.Future

/**
 * This class is responsible for creating coroutine jobs.
 *
 * Call a method to create a JobWrapper.
 *
 */
object Crouton {
    /**
     * Create an asynchronous job that runs one time.
     *
     * @param runnable [Runnable] to run
     * @return the created [JobWrapper]
     */
    @JvmStatic
    fun async(runnable : Runnable) : JobWrapper {
        val wrapper = JobWrapper()
        val job = GlobalScope.launch {
            async {
                runnable.run()
            }
        }

        wrapper.setJob(job)
        return wrapper
    }

    /**
     * Create an asynchronous job that runs one time with an initial delay.
     *
     * @param runnable [Runnable] to run
     * @param delay [Long] initial delay
     * @return the created [JobWrapper]
     */
    @JvmStatic
    fun asyncDelayed(runnable : Runnable, delay: Long) : JobWrapper {
        val wrapper = JobWrapper()
        val job = GlobalScope.launch {
            async {
                kotlinx.coroutines.delay(delay)
                runnable.run()
            }
        }

        wrapper.setJob(job)
        return wrapper
    }

    /**
     * Create an asynchronous job that runs until it is cancelled, with an initial and repeating delay.
     *
     * @param runnable [Runnable] to run
     * @param initialDelay [Long] initial delay
     * @param repeatingDelay [Long] repeating delay
     * @return the created [JobWrapper]
     */
    @JvmStatic
    fun asyncRepeating(runnable: Runnable, initialDelay : Long, repeatingDelay : Long) : JobWrapper {
        val wrapper = JobWrapper()
        val job = GlobalScope.launch {
            async {
                kotlinx.coroutines.delay(initialDelay)
                while (wrapper.isRunning()) {
                    runnable.run()
                    kotlinx.coroutines.delay(repeatingDelay)
                }
            }
        }

        wrapper.setJob(job)
        return wrapper
    }

    /**
     * Create an asynchronous job that returns a [FutureJobWrapper] with an underlying [Future] that can be used after completion.
     *
     * @param callable [Callable] to call
     * @return the created [FutureJobWrapper]
     */
    @JvmStatic
    fun await(callable: Callable<*>) : JobWrapperFuture<*> {
        val wrapper: JobWrapperCompletableFuture<Any> = JobWrapperCompletableFuture()
        val job = GlobalScope.launch {
            async {
                wrapper.complete(callable.call())
            }
        }
        wrapper.getJobWrapper().setJob(job)
        return wrapper
    }

    /**
     * Delays the current coroutine by a specific duration, this functionality is blocking
     *
     * @param delay time duration
     */
    @JvmStatic
    fun sleep(delay: Long) {
        runBlocking{
            delay(delay)
        }
    }
}