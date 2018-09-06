package com.kanzi.phoneaudio.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.provider.MediaStore
import android.util.Log
import com.kanzi.phoneaudio.R
import com.kanzi.phoneaudio.data.model.Song
import com.kanzi.phoneaudio.ui.MainActivity


class MusicService : Service(),
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    companion object {
        val TAG = MusicService::class.java.simpleName
    }

    lateinit var player: MediaPlayer
    lateinit var songs: ArrayList<Song>
    var songPosition: Int = 0
    private var songTitle = ""
    private val NOTIFY_ID = 1

    private val musicBind = MusicBinder()

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
        initMusicPlayer()
    }

    private fun initMusicPlayer() {
        player.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player.setOnPreparedListener(this)
        player.setOnCompletionListener(this)
        player.setOnErrorListener(this)
    }

    fun setMusicList(songs: ArrayList<Song>) {
        this.songs = songs

    }

    override fun onBind(intent: Intent?): IBinder {
        return musicBind
    }

    override fun onUnbind(intent: Intent?): Boolean {
        player.stop()
        player.release()
        return false
    }

    fun playSong() {
        player.reset()
        val playSong: Song = songs.get(songPosition)
        songTitle = playSong.title
        val currentSong = playSong.id
        val trackUri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currentSong)

        try {
            player.setDataSource(applicationContext, trackUri)
        } catch (e: Exception) {
            Log.e(TAG, "Error setting data source", e)
        }

        player.prepareAsync()
    }

    fun setSong(songIndex: Int) {
        songPosition = songIndex
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp!!.start()
        initPlaybackWidget()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {
    }

    fun getPosn(): Int {
        return player.currentPosition
    }

    fun getDur(): Int {
        return player.duration
    }

    fun isPng(): Boolean {
        return player.isPlaying
    }

    fun pausePlayer() {
        player.pause()
    }

    fun seek(posn: Int) {
        player.seekTo(posn)
    }

    fun go() {
        player.start()
    }

    fun playPrev() {
        songPosition--;
        if (songPosition < 0) {
            songPosition = songs.size - 1
        }
        playSong()
    }

    fun playNext() {
        songPosition++;
        if (songPosition > songs.size) {
            songPosition = 0
        }
        playSong()
    }

    private fun initPlaybackWidget() {
        val notIntent = Intent(this, MainActivity::class.java)
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder: Notification.Builder = Notification.Builder(this)

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
        .setContentText(songTitle)
        val not: Notification = builder.build();

        startForeground(NOTIFY_ID, not);
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    inner class MusicBinder : Binder() {
        internal val service: MusicService
            get() = this@MusicService
    }
}