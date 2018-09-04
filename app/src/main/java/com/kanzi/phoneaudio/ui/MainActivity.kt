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
import com.kanzi.phoneaudio.data.model.Song

class MainActivity : AppCompatActivity(), MediaController.MediaPlayerControl {

    private var controller: MusicController? = null
    private var musicService: MusicService? = null
    private var playIntent: Intent? = null
    private var musicBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment()
        setController()
    }



    private val musicConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MusicBinder
            //get service
            musicService = binder.service
            //pass list
            musicService!!.setMusicList(ArrayList<Song>())//damust
            musicBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicBound = false
        }
    }

    fun openFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, MusicListFragment(), "")
                .commit()
    }

    fun setController() {
        controller = MusicController(this)
        controller!!.setMediaPlayer(this)
        controller!!.setAnchorView(findViewById(R.id.music_list))
        controller!!.isEnabled = true
//        controller.setPrevNextListeners(View.OnClickListener {  })
    }

    override fun onStart() {
        super.onStart()
        if (playIntent == null) {
            playIntent = Intent(this, MusicService::class.java)
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
            startService(playIntent)
        }
    }

    override fun isPlaying(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canSeekForward(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDuration(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBufferPercentage(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun seekTo(pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentPosition(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canSeekBackward(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAudioSessionId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canPause(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
