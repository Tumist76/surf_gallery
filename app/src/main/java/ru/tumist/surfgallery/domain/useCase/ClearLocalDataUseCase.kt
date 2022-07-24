package ru.tumist.surfgallery.domain.useCase

import ru.tumist.surfgallery.domain.repository.AuthInfoRepository
import ru.tumist.surfgallery.domain.repository.FavoritesRepository

class ClearLocalDataUseCase (
    private val authInfoRepository: AuthInfoRepository,
    private val favoritesRepository: FavoritesRepository
){
    suspend fun execute() {
        authInfoRepository.clearAuthInfo()
        favoritesRepository.clearFavorites()
    }
}