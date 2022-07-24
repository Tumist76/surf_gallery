package ru.tumist.surfgallery.domain.repository

import ru.tumist.surfgallery.domain.model.PictureModel
import ru.tumist.surfgallery.domain.model.ResultModel

interface PicturesRepository {
    suspend fun getPictures() : ResultModel<List<PictureModel>>
}