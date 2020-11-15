package com.batzalcancia.tracks.domain.entities

import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerialName("trackId")
    val id: Int,
    val artistName: String,
    val trackName: String,
    val artworkUrl30: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val trackPrice: Double,
    @SerialName("primaryGenreName")
    val genre: String,
    val longDescription: String
)

fun Track.toLocalTrack() = ItunesTrackLocal(
    id,
    artistName,
    trackName,
    artworkUrl30,
    artworkUrl60,
    artworkUrl100,
    trackPrice,
    genre,
    longDescription
)