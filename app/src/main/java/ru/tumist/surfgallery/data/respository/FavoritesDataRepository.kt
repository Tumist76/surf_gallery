@file:OptIn(ExperimentalStdlibApi::class)

package ru.tumist.surfgallery.data.respository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.tumist.surfgallery.data.local.LocalDataStore
import ru.tumist.surfgallery.data.model.AuthInfoDto
import ru.tumist.surfgallery.data.model.FavoritesDto
import ru.tumist.surfgallery.domain.repository.FavoritesRepository

class FavoritesDataRepository(
    private val moshi: Moshi,
    private val localDataStore: LocalDataStore,
    ) : FavoritesRepository {

    private val jsonAdapter: JsonAdapter<FavoritesDto> = moshi.adapter<FavoritesDto>()

    override suspend fun getFavorites(): List<Int> {
        val result = localDataStore.get(FAVORITES_KEY) ?: return listOf<Int>()
        val favoritesIdsList : List<Int>
        withContext(Dispatchers.IO) {
            favoritesIdsList = jsonAdapter.fromJson(result)?.favoritesIds ?: listOf<Int>()
        }
        return favoritesIdsList
    }

    override suspend fun setFavorites(id: Int, newValue: Boolean) {
        val favList = getFavorites().toMutableList()

        if (!favList.contains(id)) {
            if (newValue) favList.add(id)
        } else {
            if (!newValue) favList.remove(id)
        }
        val newList = FavoritesDto(favoritesIds = favList)
        localDataStore.set(FAVORITES_KEY, jsonAdapter.toJson(newList))
    }

    override suspend fun clearFavorites() {
        localDataStore.removeEntry(FAVORITES_KEY)
    }


    companion object {
        private const val FAVORITES_KEY = "favorites"
    }
}