package com.batzalcancia.tracks.data.remote

import com.batzalcancia.tracks.domain.entities.Track

interface ItunesTrackRemoteSource {
    suspend fun getAllItunesTrack(): List<Track>
}