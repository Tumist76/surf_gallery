package ru.tumist.surfgallery.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.tumist.surfgallery.domain.model.AuthInfo

@JsonClass(generateAdapter = true)
data class AuthInfoDto (
    @Json(name = "token")
    val token : String,
    @Json(name = "user_info")
    val userInfo : UserInfoDto,
)

fun AuthInfoDto.toDomain() = AuthInfo(
    token = token,
    userInfo = userInfo.toDomain(),
)

fun AuthInfo.fromDomain() = AuthInfoDto(
    token = token,
    userInfo = userInfo.fromDomain(),
)
