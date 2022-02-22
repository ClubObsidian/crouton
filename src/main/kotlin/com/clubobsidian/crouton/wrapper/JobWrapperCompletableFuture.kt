package com.clubobsidian.crouton.wrapper

import java.util.concurrent.CompletableFuture

class JobWrapperCompletableFuture<T> : CompletableFuture<T>(), JobWrapperFuture<T> {

    private val wrapper: JobWrapper = JobWrapper()

    override fun getJobWrapper(): JobWrapper {
        return this.wrapper
    }
}