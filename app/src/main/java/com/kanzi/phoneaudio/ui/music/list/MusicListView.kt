package com.kanzi.phoneaudio.ui.music.list

import com.kanzi.phoneaudio.data.model.Song
import com.kanzi.phoneaudio.ui.base.MvpView

interface MusicListView : MvpView {
    fun showMusicList(musicList: ArrayList<Song>)

}