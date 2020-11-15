package com.batzalcancia.itunes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ItunesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("Hello")
    }
}
