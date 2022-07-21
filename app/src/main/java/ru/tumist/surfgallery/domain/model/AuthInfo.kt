package ru.tumist.surfgallery.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class AuthInfo (
    val token : String,
    val userInfo : UserInfo,
)
