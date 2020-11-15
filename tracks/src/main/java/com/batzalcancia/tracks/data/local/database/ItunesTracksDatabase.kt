package com.batzalcancia.tracks.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.batzalcancia.tracks.data.local.dao.ItunesTracksDao
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal

@Database(entities = [ItunesTrackLocal::class], version = 1)
abstract class ItunesTracksDatabase : RoomDatabase() {
    abstract fun getItunesTracksDao(): ItunesTracksDao

}