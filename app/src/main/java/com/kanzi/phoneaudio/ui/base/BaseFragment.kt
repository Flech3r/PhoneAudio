package com.kanzi.phoneaudio.ui.base

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kanzi.phoneaudio.di.component.FragmentComponent
import com.kanzi.phoneaudio.utils.extensions.getAppComponent

abstract class BaseFragment: Fragment() {

    protected var fragmentComponent: FragmentComponent? = null
    protected var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater?.inflate(getLayoutResId(), container, false)
        fragmentComponent = mRootView!!.context.getAppComponent().fragmentComponent()
        return mRootView
    }

    override fun onDestroy() {
        fragmentComponent = null
        super.onDestroy()
    }

    protected abstract fun getLayoutResId(): Int
}