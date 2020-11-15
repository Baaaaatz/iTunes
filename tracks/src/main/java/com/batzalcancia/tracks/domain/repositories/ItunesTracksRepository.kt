package com.batzalcancia.tracks.domain.repositories

import com.batzalcancia.tracks.domain.entities.Track
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal

interface ItunesTracksRepository {

    suspend fun getAllItunesTracks(): List<Track>

    suspend fun saveAllItunesTracksLocally(list: List<ItunesTrackLocal>)

    suspend fun getAllItunesTracksLocally(): List<ItunesTrackLocal>

    suspend fun saveTrackToCache(trackLocal: ItunesTrackLocal)

    suspend fun getTrackFromCache(): ItunesTrackLocal?

    suspend fun clearTrack()

}

