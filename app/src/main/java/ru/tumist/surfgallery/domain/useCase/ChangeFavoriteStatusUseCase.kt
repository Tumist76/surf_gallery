package ru.tumist.surfgallery.domain.useCase

import ru.tumist.surfgallery.domain.model.PictureModel
import ru.tumist.surfgallery.domain.repository.FavoritesRepository

class ChangeFavoriteStatusUseCase(private val favoritesRepository: FavoritesRepository) {
    suspend fun execute(pictures: List<PictureModel>, id: Int, value: Boolean): List<PictureModel> {
        favoritesRepository.setFavorites(id, value)
        return pictures.map { pic ->
            if (pic.id == id) {
                pic.copy(isFavorite = value)
            }
            else {
                pic
            }
        }
    }
}