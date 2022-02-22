package com.clubobsidian.crouton.wrapper

import java.util.concurrent.CompletableFuture

class CroutonWrapperCompletableFuture<T> : CompletableFuture<T>(), CroutonWrapperFuture<T> {

    private val wrapper: CroutonWrapper = CroutonWrapper()

    override fun getCroutonWrapper(): CroutonWrapper {
        return this.wrapper
    }
}