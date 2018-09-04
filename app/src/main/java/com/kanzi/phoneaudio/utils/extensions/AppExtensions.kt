package com.kanzi.phoneaudio.utils.extensions

import android.content.Context
import com.kanzi.phoneaudio.MainApp
import com.kanzi.phoneaudio.di.component.AppComponent

fun Context.getAppComponent(): AppComponent = (applicationContext as MainApp).appComponent