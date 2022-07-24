package ru.tumist.surfgallery.domain.repository

import ru.tumist.surfgallery.domain.model.AuthInfo

interface FavoritesRepository {
    suspend fun getFavorites() : List<Int>?
    suspend fun setFavorites(id: Int, newValue: Boolean)
    suspend fun clearFavorites()
}