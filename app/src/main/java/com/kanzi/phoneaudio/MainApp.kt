package com.kanzi.phoneaudio

import android.app.Application
import com.kanzi.phoneaudio.di.component.AppComponent
import com.kanzi.phoneaudio.di.component.DaggerAppComponent

class MainApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setupComponent()
    }

    private fun setupComponent() {
        appComponent = DaggerAppComponent.builder().build()
    }
}