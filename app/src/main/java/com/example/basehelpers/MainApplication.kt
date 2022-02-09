package com.example.basehelpers

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class MainApplication : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
    }
}