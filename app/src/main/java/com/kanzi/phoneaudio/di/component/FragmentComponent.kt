package com.kanzi.phoneaudio.di.component

import com.kanzi.phoneaudio.di.scope.PerView
import com.kanzi.phoneaudio.ui.music.list.MusicListFragment
import dagger.Subcomponent

@PerView
@Subcomponent
interface FragmentComponent {

    fun inject(fragment: MusicListFragment)
}