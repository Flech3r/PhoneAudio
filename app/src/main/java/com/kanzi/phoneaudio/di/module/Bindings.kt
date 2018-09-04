package com.kanzi.phoneaudio.di.module

import dagger.Binds
import dagger.Module

@Module
abstract class Bindings {

    @Binds
    internal abstract fun bind(test: String): String
}