package ru.tumist.surfgallery.data.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name = "phone")
    val phone : String,
    @Json(name = "password")
    val password : String,
)
