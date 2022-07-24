package ru.tumist.surfgallery.domain.model

import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureModel(
    val id: Int,
    val title: String,
    val content: String,
    val photoUrl: String?,
    val publicationDate: Date,
    val isFavorite: Boolean,
) : Parcelable

fun PictureModel.dateToViewString() : String {
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormatter.format(publicationDate)
}

