package ru.tumist.surfgallery.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.tumist.surfgallery.domain.model.UserInfo

@JsonClass(generateAdapter = true)
data class UserInfoDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "firstName")
    val firstName: String?,
    @Json(name = "lastName")
    val lastName: String?,
    @Json(name = "avatar")
    val avatar: String?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "about")
    val about: String?,
)

fun UserInfoDto.toDomain() = UserInfo(
    id = id,
    phone = phone,
    email = email,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar,
    city = city,
    about = about,
)

fun UserInfo.fromDomain() = UserInfoDto(
    id = id,
    phone = phone,
    email = email,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar,
    city = city,
    about = about,
)