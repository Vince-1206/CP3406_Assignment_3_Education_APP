package com.vince.vocabquest

import android.app.Application
import com.vince.vocabquest.di.AppContainer
import com.vince.vocabquest.di.DefaultAppContainer

class VocabQuestApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}
