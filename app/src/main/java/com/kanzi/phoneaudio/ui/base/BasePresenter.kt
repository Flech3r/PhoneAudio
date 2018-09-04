package com.kanzi.phoneaudio.ui.base

import android.content.Context

abstract class BasePresenter<T : MvpView> {

    protected var view: T? = null
    protected var context: Context? = null

    fun bind(view: T, context: Context) {
        this.view = view
        this.context = context
    }

    private fun unbind() {
        this.view = null
    }

    fun destroy() {
        unbind()
    }
}