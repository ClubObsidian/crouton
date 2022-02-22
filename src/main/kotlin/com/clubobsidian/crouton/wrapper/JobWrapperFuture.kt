package com.clubobsidian.crouton.wrapper

import java.util.concurrent.Future

interface JobWrapperFuture<T> : Future<T> {

    fun getJobWrapper(): JobWrapper

    fun isRunning(): Boolean {
        return this.getJobWrapper().isRunning()
    }
}