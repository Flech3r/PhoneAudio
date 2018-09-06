package com.kanzi.phoneaudio.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.kanzi.phoneaudio.R
import com.kanzi.phoneaudio.data.model.Song
import com.kanzi.phoneaudio.ui.MainActivityView

class SongAdapter(private val songs: ArrayList<Song>,
                  cotext: Context,
                  private val view: MainActivityView): BaseAdapter() {

    companion object {
        val TAG = SongAdapter::class.java.simpleName
    }

    private var songInf: LayoutInflater = LayoutInflater.from(cotext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val songLayout: LinearLayout = songInf.inflate(
                R.layout.song_input_view,
                parent,
                false
        ) as LinearLayout
        val songView: TextView =  songLayout.findViewById(R.id.song_title) as TextView
        val artistView: TextView = songLayout.findViewById(R.id.song_artist) as TextView

        val currentSong: Song = songs.get(position)
        songView.setText(currentSong.title)
        artistView.setText(currentSong.artist)
        songLayout.setTag(position)

        return songLayout
    }

    override fun getItem(position: Int) {
        Log.i(TAG, "Selected m")
    }

    override fun getItemId(position: Int): Long {
        view.songPicked(position)
        return 0
    }

    override fun getCount(): Int {
        return songs.size
    }
}