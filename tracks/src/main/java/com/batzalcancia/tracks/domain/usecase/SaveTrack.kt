package com.batzalcancia.tracks.domain.usecase

import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal
import com.batzalcancia.tracks.domain.repositories.ItunesTracksRepository
import javax.inject.Inject

/**
 * @param itunesTracksRepository - choose from Remote, Local or dao what to use in this usecase
 */
class SaveTrack @Inject constructor(private val itunesTracksRepository: ItunesTracksRepository) {
    suspend operator fun invoke(track: ItunesTrackLocal) {
        itunesTracksRepository.saveTrackToCache(track)
    }
}