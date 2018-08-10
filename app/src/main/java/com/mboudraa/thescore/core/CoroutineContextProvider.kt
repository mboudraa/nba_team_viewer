package com.mboudraa.thescore.core

import kotlin.coroutines.experimental.CoroutineContext

object CoroutineContextProvider {

    lateinit var UI: CoroutineContext
        private set

    lateinit var IO: CoroutineContext
        private set


    fun initWith(factory: Factory) {
        UI = factory.ui()
        IO = factory.io()
    }

    interface Factory {
        fun ui(): CoroutineContext
        fun io(): CoroutineContext
    }

}
