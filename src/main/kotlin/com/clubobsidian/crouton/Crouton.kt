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
import com.clubobsidian.crouton.wrapper.FutureJobWrapper
import kotlinx.coroutines.*
import java.lang.Runnable

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import java.util.stream.Stream

open class Crouton() {

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

    fun await(future: Future<*>) : FutureJobWrapper {
        val wrapper = FutureJobWrapper()
        val completedFuture = CompletableFuture<Any>()
        val job = GlobalScope.launch {
            async {
                completedFuture.complete(future.get())
                println("complete")
            }
        }

        wrapper.setFuture(completedFuture)
        wrapper.setJob(job)
        return wrapper
    }

    fun delay(delay: Long) {
        runBlocking {
            kotlinx.coroutines.delay(delay)
        }
    }
}