package ru.tumist.surfgallery.domain.useCase

import ru.tumist.surfgallery.domain.model.PictureModel
import ru.tumist.surfgallery.domain.model.ResultModel
import ru.tumist.surfgallery.domain.repository.FavoritesRepository
import ru.tumist.surfgallery.domain.repository.PicturesRepository

class GetPicturesUseCase(
    private val picturesRepository: PicturesRepository,
    private val favoritesRepository: FavoritesRepository,
) {
    suspend fun execute(): ResultModel<List<PictureModel>> {
        val result = picturesRepository.getPictures()
        if (result is ResultModel.Success) {
            val favList = favoritesRepository.getFavorites() ?: return result
            val resultWithFavStatus = result.value.map { fav ->
                fav.copy(isFavorite = favList.contains(fav.id))
            }
            return ResultModel.Success(resultWithFavStatus)
        }
        return result
    }
}