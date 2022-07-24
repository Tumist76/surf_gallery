package ru.tumist.surfgallery.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavoritesDto (
    @Json(name = "favorites_ids")
    val favoritesIds : List<Int>,
)