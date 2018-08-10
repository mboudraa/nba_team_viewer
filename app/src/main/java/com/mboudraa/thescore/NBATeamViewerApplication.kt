package com.mboudraa.thescore

import android.app.Application
import com.mboudraa.thescore.core.CoroutineContextProvider
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI

class NBATeamViewerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineContextProvider.initWith(object : CoroutineContextProvider.Factory {
            override fun ui() = UI
            override fun io() = CommonPool
        })
    }
}