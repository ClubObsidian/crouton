package com.clubobsidian.crouton.wrapper

import java.util.concurrent.Future;

class FutureJobWrapper: JobWrapper {

    constructor() : super();

    private var future: Future<Any>? = null;

    fun getFuture(): Future<Any>? {
        return this.future;
    }

    fun setFuture(future: Future<Any>?) {
        this.future = future;
    }
}