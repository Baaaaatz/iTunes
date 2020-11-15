package com.batzalcancia.tracks.data.repositories

import com.batzalcancia.tracks.data.local.ItunesTrackLocalSource
import com.batzalcancia.tracks.data.local.dao.ItunesTracksDao
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal
import com.batzalcancia.tracks.data.remote.ItunesTrackRemoteSource
import com.batzalcancia.tracks.domain.repositories.ItunesTracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * @param itunesTrackLocalSource Local source for Repository includes cache
 * @param itunesTracksDao Local storage for Repository includes room database
 * @param itunesTracksRemoteSource Remote source for Repository include api calls
 */
class ItunesTracksRepositoryImpl @Inject constructor(
    private val itunesTracksRemoteSource: ItunesTrackRemoteSource,
    private val itunesTracksDao: ItunesTracksDao,
    private val itunesTrackLocalSource: ItunesTrackLocalSource
) : ItunesTracksRepository {

    override suspend fun getAllItunesTracks() =
        withContext(Dispatchers.IO) { itunesTracksRemoteSource.getAllItunesTrack() }

    override suspend fun saveAllItunesTracksLocally(list: List<ItunesTrackLocal>) =
        withContext(Dispatchers.IO) {
            itunesTracksDao.insertAllTracks(list)
        }

    override suspend fun getAllItunesTracksLocally() = withContext(Dispatchers.IO) {
        itunesTracksDao.getAllItunesTracks()
    }

    override suspend fun saveTrackToCache(trackLocal: ItunesTrackLocal) {
        itunesTrackLocalSource.saveTrack(Json.encodeToString(trackLocal))
    }

    override suspend fun getTrackFromCache(): ItunesTrackLocal? {
        return itunesTrackLocalSource.getTrack()?.let {
            Json.decodeFromString(
                ItunesTrackLocal.serializer(),
                it
            )
        }
    }

    override suspend fun clearTrack() {
        itunesTrackLocalSource.clearTrack()
    }

}