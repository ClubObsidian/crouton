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

import com.clubobsidian.crouton.wrapper.CroutonWrapper
import com.clubobsidian.crouton.wrapper.CroutonWrapperCompletableFuture
import com.clubobsidian.crouton.wrapper.CroutonWrapperFuture
import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.concurrent.Callable

import java.util.concurrent.Future
import java.util.function.BiConsumer
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

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
     * @return the created [CroutonWrapper]
     */
    @JvmStatic
    fun async(runnable : Runnable) : CroutonWrapper {
        val wrapper = CroutonWrapper()
        wrapper.setJob(GlobalScope.launch {
            runnable.run()
        })
        return wrapper
    }

    /**
     * Create an asynchronous job that runs one time with an initial delay.
     *
     * @param runnable [Runnable] to run
     * @param delay [Long] initial delay
     * @return the created [CroutonWrapper]
     */
    @JvmStatic
    fun asyncDelayed(runnable : Runnable, delay: Long) : CroutonWrapper {
        val wrapper = CroutonWrapper()
        wrapper.setJob(GlobalScope.launch {
            delay(delay)
            runnable.run()
        })
        return wrapper
    }

    /**
     * Create an asynchronous job that runs until it is cancelled, with an initial and repeating delay.
     *
     * @param runnable [Runnable] to run
     * @param initialDelay [Long] initial delay
     * @param repeatingDelay [Long] repeating delay
     * @return the created [CroutonWrapper]
     */
    @JvmStatic
    fun asyncRepeating(runnable: Runnable, initialDelay : Long, repeatingDelay : Long) : CroutonWrapper {
        val wrapper = CroutonWrapper()
        wrapper.setJob(GlobalScope.launch {
            delay(initialDelay)
            while (wrapper.isRunning()) {
                runnable.run()
                delay(repeatingDelay)
            }
        })
        return wrapper
    }

    /**
     * Create an asynchronous job that returns a [FutureJobWrapper] with an underlying [Future] that can be used after completion.
     *
     * @param callable [Callable] to call
     * @return the created [FutureJobWrapper]
     */
    @JvmStatic
    fun await(callable: Callable<*>) : CroutonWrapperFuture<*> {
        val wrapper: CroutonWrapperCompletableFuture<Any> = CroutonWrapperCompletableFuture()
        wrapper.getCroutonWrapper().setJob(GlobalScope.launch {
                wrapper.complete(callable.call())
        })
        return wrapper
    }
}