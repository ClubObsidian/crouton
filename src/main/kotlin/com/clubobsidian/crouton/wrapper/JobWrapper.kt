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
package com.clubobsidian.crouton.wrapper

import kotlinx.coroutines.*

import java.util.concurrent.atomic.AtomicBoolean

open class JobWrapper() {

    private val running = AtomicBoolean(true)
    private var job: Job? = null

    fun getJob() : Job? {
        return this.job
    }

    fun setJob(job : Job?) {
        this.job = job
    }

    fun isRunning() : Boolean {
        return this.running.get() && this.job!!.isActive
    }

    fun stop() {
        this.running.set(false)
        this.job!!.cancel()
    }
}