package com.kanzi.phoneaudio.ui.music.list

import android.os.Bundle
import android.util.Log
import android.view.View
import com.kanzi.phoneaudio.R
import com.kanzi.phoneaudio.adapters.SongAdapter
import com.kanzi.phoneaudio.data.model.Song
import com.kanzi.phoneaudio.ui.base.BaseFragment
import kotlinx.android.synthetic.main.music_list_layout.*
import javax.inject.Inject

class MusicListFragment: BaseFragment(), MusicListView {

    companion object {
        val TAG = MusicListFragment::class.java.simpleName
    }

    @Inject
    lateinit var presenter: MusicListPresenter



    override fun getLayoutResId() = R.layout.music_list_layout

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentComponent?.inject(this)
        presenter.bind(this, mRootView!!.context)
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchAllAvailableMusicFiles()
    }

    override fun showMusicList(songList: ArrayList<Song>) {
        Log.i(TAG, "spong list: $songList")
        val adapter: SongAdapter = SongAdapter(songList, mRootView!!.context)
        music_list.adapter = adapter
        adapter.notifyDataSetChanged()
        music_list.invalidate()
    }
}