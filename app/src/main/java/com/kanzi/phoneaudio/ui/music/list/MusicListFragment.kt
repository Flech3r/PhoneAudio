package com.kanzi.phoneaudio.ui.music.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kanzi.phoneaudio.R
import com.kanzi.phoneaudio.data.model.Song
import com.kanzi.phoneaudio.ui.base.BaseFragment
import javax.inject.Inject

class MusicListFragment: BaseFragment(), MusicListView {

    companion object {
        val TAG = MusicListFragment::class.java.simpleName
    }

    @Inject
    lateinit var presenter: MusicListPresenter

    override fun getLayoutResId() = R.layout.music_list_layout

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentComponent?.inject(this)
        presenter.bind(this, mRootView!!.context)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchAllAvailableMusicFiles()
    }

    override fun showMusicList(songList: ArrayList<Song>) {
        Log.i(TAG, "spong list: $songList")
    }
}