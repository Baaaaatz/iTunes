package com.batzalcancia.tracks.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ItunesResponse<T> (
    val resultCount: Int,
    val results: List<T>
)