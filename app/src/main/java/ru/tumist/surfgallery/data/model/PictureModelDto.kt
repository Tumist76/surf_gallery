package ru.tumist.surfgallery.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.tumist.surfgallery.domain.model.PictureModel
import java.util.*

@JsonClass(generateAdapter = true)
data class PictureModelDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "photoUrl")
    val photoUrl: String,
    @Json(name = "publicationDate")
    val publicationDate: Date,
)

fun PictureModelDto.toDomain() = PictureModel(
    id = id,
    title = title,
    content = content,
    publicationDate = publicationDate,
    photoUrl = photoUrl,
    isFavorite = false,
)
