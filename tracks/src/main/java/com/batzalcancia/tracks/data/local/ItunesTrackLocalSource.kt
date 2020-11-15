package com.batzalcancia.tracks.data.local

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.clear
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

/**
 * @param dataStore - provides a safe and durable way to store small amounts of data, such as preferences
 * and application state
 */
class ItunesTrackLocalSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val TRACK_DETAILS = preferencesKey<String>("TRACK_DETAILS")

    /**
     * Get Saved Track in cache
     */
    suspend fun getTrack() = dataStore.data.map {
        it[TRACK_DETAILS]
    }.first()

    /**
     *  Save a Track in cache
     */
    suspend fun saveTrack(trackDetails: String) {
        dataStore.edit { preference ->
            preference[TRACK_DETAILS] = trackDetails
        }
    }

    /**
     *  Clear the track
     */
    suspend fun clearTrack() {
        dataStore.edit {
            it.clear()
        }
    }
}