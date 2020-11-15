package com.batzalcancia.tracks.presentation.features.trackDetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal
import com.batzalcancia.tracks.domain.usecase.ClearTrack
import com.batzalcancia.tracks.domain.usecase.SaveTrack
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


/**
 * @param saveTrack Saves the track to persist what page to open
 * @param clearTrack Clears the track that is saved
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TrackDetailsViewModel @ViewModelInject constructor(
    private val saveTrack: SaveTrack,
    private val clearTrack: ClearTrack
) : ViewModel() {

    /**
     * @param track Track that will be saved
     */
    fun executeSaveTrack(track: ItunesTrackLocal) {
        viewModelScope.launch {
            saveTrack(track)
        }
    }

    /**
     * Clears the saved track
     */
    fun executeClearTrack() {
        viewModelScope.launch {
            clearTrack()
        }
    }
}