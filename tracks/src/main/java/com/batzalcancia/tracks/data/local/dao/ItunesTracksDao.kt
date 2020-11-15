package com.batzalcancia.tracks.data.local.dao

import androidx.room.*
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal

@Dao
interface ItunesTracksDao  {

    @Query("SELECT * FROM tracks")
    fun getAllItunesTracks(): List<ItunesTrackLocal>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAllTracks(tracks: List<ItunesTrackLocal>)

}