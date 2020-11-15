package com.batzalcancia.tracks.data.remote

import com.batzalcancia.tracks.data.api.ItunesTracksApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * @param api Api Call for itunes
 */
class ItunesTrackRemoteSourceImpl @Inject constructor(
    private val api: ItunesTracksApi
) : ItunesTrackRemoteSource {
    override suspend fun getAllItunesTrack() = withContext(Dispatchers.IO) {
        api.getAllItunesTrack().results
    }
}