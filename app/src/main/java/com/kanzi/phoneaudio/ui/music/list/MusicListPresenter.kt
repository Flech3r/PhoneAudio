package com.kanzi.phoneaudio.ui.music.list

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.kanzi.phoneaudio.data.model.Song
import com.kanzi.phoneaudio.ui.base.BasePresenter
import javax.inject.Inject



class MusicListPresenter @Inject constructor() : BasePresenter<MusicListView>() {

    companion object {
        val TAG = MusicListPresenter::class.java.simpleName
    }

    private val songList: ArrayList<Song> = ArrayList()

    fun fetchAllAvailableMusicFiles() {
        findMusicFiles()
        view?.showMusicList(songList)
    }

    private fun findMusicFiles() {
        songList.clear()

        val musicResolver: ContentResolver = context!!.contentResolver

        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION)
        val musicCursor: Cursor = musicResolver
                .query(musicUri,
                        projection,
                        selection,
                        null,
                        MediaStore.Audio.Media.TITLE)

        if (musicCursor != null && musicCursor.moveToFirst()) {
            val titleColumn: Int = musicCursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE)
            val idColumn: Int = musicCursor
                    .getColumnIndex(MediaStore.Audio.Media._ID)
            val artistColumn = musicCursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST)

            do {
                val thisId: Long = musicCursor.getLong(idColumn)
                val thisTitle: String = musicCursor.getString(titleColumn)
                val thisArtist: String = musicCursor.getString(artistColumn)

                songList.add(Song(thisId, thisTitle, thisArtist))
            } while (musicCursor.moveToNext())

        }
    }
}