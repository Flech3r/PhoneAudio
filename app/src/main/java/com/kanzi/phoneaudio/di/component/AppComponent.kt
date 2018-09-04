package com.kanzi.phoneaudio.di.component

import com.kanzi.phoneaudio.di.module.Bindings
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(Bindings::class)])
interface AppComponent {
    fun fragmentComponent(): FragmentComponent
}