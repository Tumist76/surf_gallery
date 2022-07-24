package ru.tumist.surfgallery.data.network

import retrofit2.http.GET
import ru.tumist.surfgallery.data.model.PictureModelDto

interface PicturesApi {
    @GET("picture")
    suspend fun getPictures() : List<PictureModelDto>
}