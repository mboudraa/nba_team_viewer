package com.mboudraa.thescore

import com.mboudraa.thescore.core.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import org.junit.rules.ExternalResource

class CoroutineContextRule : ExternalResource() {
    override fun before() {
        CoroutineContextProvider.initWith(object : CoroutineContextProvider.Factory {
            override fun ui() = Unconfined
            override fun io() = Unconfined
        })
    }
}