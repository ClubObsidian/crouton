package com.clubobsidian.crouton

import kotlinx.coroutines.*;
import java.lang.Runnable

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

import java.util.concurrent.atomic.AtomicBoolean;

class Crouton public constructor() {

    private var job: Job? = null;
    private val running = AtomicBoolean(true);

    fun createAsyncJob(runnable : Runnable) : Job
    {
        if(this.job == null)
        {
            val newJob = GlobalScope.launch()
            {
                async {
                    runnable.run();
                }
            };

            this.job = newJob;
        }
        return this.job!!;
    }

    fun createAsyncRepeatingJob(runnable: Runnable, initialDelay : Long, repeatingDelay : Long) : Job
    {
        if(this.job == null)
        {
            val newJob = GlobalScope.launch()
            {
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

    fun createAsyncWait(future: Future<Any>) : Future<Any>
    {
        val completedFuture = CompletableFuture<Any>();
        if(this.job == null)
        {
            val newJob = GlobalScope.launch()
            {
                async {
                    completedFuture.complete(future.get());
                }
            };
            this.job = newJob;
        }
        return future;
    }

    suspend fun delayJob(millis: Long)
    {
        delay(millis);
    }

    fun isRunning() : AtomicBoolean
    {
        return this.running;
    }

    fun getJob() : Job
    {
        return this.job!!;
    }

    fun stop()
    {
        this.running.set(false);
    }
}