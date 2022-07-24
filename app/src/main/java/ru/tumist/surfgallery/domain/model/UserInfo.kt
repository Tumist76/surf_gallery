package ru.tumist.surfgallery.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class UserInfo(
    val id: String,
    val phone: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val avatar: String?,
    val city: String?,
    val about: String?,
)
