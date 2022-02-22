package com.clubobsidian.crouton.wrapper

import java.util.concurrent.Future

interface CroutonWrapperFuture<T> : Future<T> {

    fun getCroutonWrapper(): CroutonWrapper

    fun isRunning(): Boolean {
        return this.getCroutonWrapper().isRunning()
    }
}