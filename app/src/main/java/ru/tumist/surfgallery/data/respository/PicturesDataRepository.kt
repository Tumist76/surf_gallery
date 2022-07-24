package ru.tumist.surfgallery.data.respository

import ru.tumist.surfgallery.data.model.request.AuthRequest
import ru.tumist.surfgallery.data.model.toDomain
import ru.tumist.surfgallery.data.network.PicturesApi
import ru.tumist.surfgallery.data.utils.makeApiRequest
import ru.tumist.surfgallery.domain.model.PictureModel
import ru.tumist.surfgallery.domain.model.ResultModel
import ru.tumist.surfgallery.domain.repository.PicturesRepository

class PicturesDataRepository(private val picturesApi: PicturesApi) : PicturesRepository{
    override suspend fun getPictures(): ResultModel<List<PictureModel>> {
        return makeApiRequest {
            val result = picturesApi.getPictures()
            result.map { pic -> pic.toDomain() }
        }
    }
}