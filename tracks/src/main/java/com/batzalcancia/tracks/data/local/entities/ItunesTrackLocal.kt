package com.batzalcancia.tracks.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "tracks")
@Serializable
data class ItunesTrackLocal(
    @PrimaryKey
    val id: Int,
    val artistName: String,
    val trackName: String,
    val artworkUrl30: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val trackPrice: Double,
    val genre: String,
    val longDescription: String
)