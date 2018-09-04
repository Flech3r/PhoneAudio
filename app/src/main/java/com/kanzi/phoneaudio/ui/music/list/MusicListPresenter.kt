package com.kanzi.phoneaudio.ui.music.list

import android.content.ContentResolver
import android.database.Cursor
import com.kanzi.phoneaudio.data.model.Song
import com.kanzi.phoneaudio.ui.base.BasePresenter
import javax.inject.Inject

class MusicListPresenter @Inject constructor() : BasePresenter<MusicListView>() {

    private val songList: ArrayList<Song> = ArrayList()

    fun fetchAllAvailableMusicFiles() {
        findMusicFiles()
        view?.showMusicList(songList)
    }

    private fun findMusicFiles() {
        val musicResolver: ContentResolver = context!!.contentResolver
        val musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor: Cursor = musicResolver
                .query(musicUri, null, null, null, null)

        if (musicCursor != null && musicCursor.moveToFirst()) {
            val titleColumn: Int = musicCursor
                    .getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)
            val idColumn: Int = musicCursor
                    .getColumnIndex(android.provider.MediaStore.Audio.Media._ID)
            val artistColumn = musicCursor
                    .getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST)

            do {
                val thisId: Long = musicCursor.getLong(idColumn)
                val thisTitle: String = musicCursor.getString(titleColumn)
                val thisArtist: String = musicCursor.getString(artistColumn)

                songList.add(Song(thisId, thisTitle, thisArtist))
            } while (musicCursor.moveToNext())

        }
    }
}