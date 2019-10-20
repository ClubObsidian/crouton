package com.clubobsidian.crouton.wrapper

import kotlinx.coroutines.*;

import java.util.concurrent.atomic.AtomicBoolean

open class JobWrapper public constructor() {

    private val running = AtomicBoolean(true);
    private var job: Job? = null;

    fun getJob() : Job? {
        return this.job;
    }

    fun setJob(job : Job?) {
        this.job = job;
    }

    fun isRunning() : AtomicBoolean {
        return this.running;
    }
}