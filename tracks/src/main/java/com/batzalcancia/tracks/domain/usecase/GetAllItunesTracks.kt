package com.batzalcancia.tracks.domain.usecase

import com.batzalcancia.tracks.domain.entities.toLocalTrack
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal
import com.batzalcancia.tracks.domain.repositories.ItunesTracksRepository
import javax.inject.Inject

/**
 * @param itunesTracksRepository - choose from Remote, Local or dao what to use in this usecase
 */
class GetAllItunesTracks @Inject constructor(private val itunesTracksRepository: ItunesTracksRepository) {
    suspend operator fun invoke(): List<ItunesTrackLocal> {

        val cachedTracks = itunesTracksRepository.getAllItunesTracksLocally()

        if (cachedTracks.isEmpty()) {
            itunesTracksRepository.getAllItunesTracks().map {
                it.toLocalTrack()
            }.run {
                itunesTracksRepository.saveAllItunesTracksLocally(this)

            }
        }

        return itunesTracksRepository.getAllItunesTracksLocally()

    }
}