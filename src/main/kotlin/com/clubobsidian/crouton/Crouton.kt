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

import kotlinx.coroutines.*;
import java.lang.Runnable

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

import java.util.concurrent.atomic.AtomicBoolean;

class Crouton public constructor() {

    private var job: Job? = null;
    private val running = AtomicBoolean(true);

    fun async(runnable : Runnable) : Job {
        if(this.job == null) {
            val newJob = GlobalScope.launch() {
                async {
                    runnable.run();
                }
            };

            this.job = newJob;
        }
        return this.job!!;
    }

    fun asyncDelayed(runnable : Runnable, delay: Long) : Job {
        if(this.job == null) {
            val newJob = GlobalScope.launch() {
                async {
                    delay(delay);
                    runnable.run();
                }
            };

            this.job = newJob;
        }
        return this.job!!;
    }

    fun asyncRepeating(runnable: Runnable, initialDelay : Long, repeatingDelay : Long) : Job {
        if(this.job == null) {
            val newJob = GlobalScope.launch() {
                async {
                    delay(initialDelay);
                    while (isRunning().get()) {
                        runnable.run();
                        delay(repeatingDelay);
                    }
                }
            };

            this.job = newJob;
        }
        return this.job!!;
    }

    fun await(future: Future<Any>) : Future<Any> {
        val completedFuture = CompletableFuture<Any>();
        if (this.job == null) {
            val newJob = GlobalScope.launch() {
                async {
                    completedFuture.complete(future.get());
                }
            };
            this.job = newJob;
        }
        return future;
    }

    fun isRunning() : AtomicBoolean {
        return this.running;
    }

    fun getJob() : Job {
        return this.job!!;
    }

    fun stop() {
        this.running.set(false);
    }
}