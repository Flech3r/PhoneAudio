package com.kanzi.phoneaudio.ui

import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.kanzi.phoneaudio.R
import com.kanzi.phoneaudio.controllers.MusicController
import com.kanzi.phoneaudio.service.MusicService
import com.kanzi.phoneaudio.ui.music.list.MusicListFragment
import android.content.ComponentName
import android.content.Context
import com.kanzi.phoneaudio.service.MusicService.MusicBinder
import android.os.IBinder
import android.util.Log
import android.view.View
import com.kanzi.phoneaudio.data.Container
import com.kanzi.phoneaudio.data.model.Song

class MainActivity : AppCompatActivity(), MediaController.MediaPlayerControl, MainActivityView {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var controller: MusicController
    private lateinit var musicService: MusicService
    private var playIntent: Intent? = null
    private var musicBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment()
        initController()
    }


    private val musicConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MusicBinder
            //get service
            musicService = binder.service
            //pass list
            musicService.setMusicList(Container.songList)
            musicBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicBound = false
        }
    }

    fun openFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, MusicListFragment(this), "")
                .commit()
    }

    fun initController() {
        controller = MusicController(this)
        controller.setPrevNextListeners({ playNext() }, { playPrev() })
        controller.setMediaPlayer(this)
        controller.setAnchorView(findViewById(R.id.music_list))
        controller.isEnabled = true

    }

    override fun onStart() {
        super.onStart()
        if (playIntent == null) {
            Log.i(TAG, "initing")
            playIntent = Intent(this, MusicService::class.java)
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
            startService(playIntent)
        }
    }

    override fun songPicked(position: Int) {
        Log.i(TAG, "wokring")
        if (musicBound) {
            musicService.setSong(position)
            musicService.playSong()
        }

    }

    private fun playNext() {
        musicService.playNext()
        controller.show(0)
    }

    private fun playPrev() {
        musicService.playPrev()
        controller.show(0)
    }

    override fun isPlaying(): Boolean {
        if (musicService != null && musicBound) {
            return musicService.isPng()
        } else {
            return false
        }
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun getDuration(): Int {
        if (musicService != null && musicBound && musicService.isPng()) {
            return musicService.getDur()
        } else {
            return 0
        }
    }

    override fun pause() {
        musicService.pausePlayer()
    }

    override fun getBufferPercentage(): Int {
        return 0
    }

    override fun seekTo(pos: Int) {
        musicService.seek(pos)
    }

    override fun getCurrentPosition(): Int {

        if (musicService != null && musicBound && musicService.isPng()) {
            return musicService.getPosn()
        } else {
            return 0
        }
    }

    override fun canSeekBackward(): Boolean {
        return true
    }

    override fun start() {
        musicService.go()
    }

    override fun getAudioSessionId(): Int {
        return 0
    }

    override fun canPause(): Boolean {
        return true
    }

    override fun onDestroy() {
        stopService(playIntent)
        super.onDestroy()
    }
}
