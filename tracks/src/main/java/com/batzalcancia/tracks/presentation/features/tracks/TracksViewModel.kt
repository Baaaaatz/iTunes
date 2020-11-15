package com.batzalcancia.tracks.presentation.features.tracks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batzalcancia.core.enums.UiState
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal
import com.batzalcancia.tracks.domain.usecase.GetAllItunesTracks
import com.batzalcancia.tracks.domain.usecase.GetTrack
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * @param getAllItunesTracks Gets all itunes tracks to be displayed in the ui
 * @param getTrack Gets the saved track to show on UI
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TracksViewModel @ViewModelInject constructor(
    private val getAllItunesTracks: GetAllItunesTracks,
    private val getTrack: GetTrack
) : ViewModel() {

    val tracksState = MutableStateFlow<UiState>(UiState.Loading)
    val tracks = MutableStateFlow<List<ItunesTrackLocal>>(emptyList())
    val savedTrack = MutableStateFlow<ItunesTrackLocal?>(null)

    init {
        executeGetAllTracks()
        viewModelScope.launch(CoroutineExceptionHandler { _, _ -> }) {
            savedTrack.value = getTrack()
        }
    }

    fun executeGetAllTracks() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            tracksState.value = UiState.Error(throwable)
        }) {
            tracksState.value = UiState.Loading
            tracks.value = getAllItunesTracks()
            tracksState.value = UiState.Complete
        }
    }

}