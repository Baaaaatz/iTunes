package com.batzalcancia.tracks.data.api

import com.batzalcancia.tracks.data.model.response.ItunesResponse
import com.batzalcancia.tracks.domain.entities.Track
import retrofit2.http.GET

interface ItunesTracksApi {

    @GET("search?term=star&country=au&media=movie&all")
    suspend fun getAllItunesTrack(): ItunesResponse<Track>

}