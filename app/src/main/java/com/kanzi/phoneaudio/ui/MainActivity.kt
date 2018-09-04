package com.kanzi.phoneaudio.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kanzi.phoneaudio.R
import com.kanzi.phoneaudio.ui.music.list.MusicListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment()
    }

    fun openFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, MusicListFragment(), "")
                .commit()
    }

}
