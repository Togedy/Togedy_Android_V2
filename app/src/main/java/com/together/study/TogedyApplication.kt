package com.together.study

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TogedyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
