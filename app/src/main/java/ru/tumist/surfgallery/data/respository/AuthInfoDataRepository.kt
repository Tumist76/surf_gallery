@file:OptIn(ExperimentalStdlibApi::class)

package ru.tumist.surfgallery.data.respository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.tumist.surfgallery.data.local.LocalDataStore
import ru.tumist.surfgallery.data.model.AuthInfoDto
import ru.tumist.surfgallery.data.model.fromDomain
import ru.tumist.surfgallery.data.model.toDomain
import ru.tumist.surfgallery.domain.model.AuthInfo
import ru.tumist.surfgallery.domain.repository.AuthInfoRepository


class AuthInfoDataRepository(
    private val moshi: Moshi,
    private val localDataStore: LocalDataStore,
) : AuthInfoRepository {

    private val jsonAdapter: JsonAdapter<AuthInfoDto> = moshi.adapter<AuthInfoDto>()

    override suspend fun getAuthInfo(): AuthInfo? {
        val result = localDataStore.get(key = AUTH_INFO_KEY) ?: return null;
        val authInfoDto = withContext(Dispatchers.IO) {
            jsonAdapter.fromJson(result)
        }
        return authInfoDto?.toDomain()
    }

    override suspend fun saveAuthInfo(authInfo: AuthInfo): Boolean {
        val authInfoString = withContext(Dispatchers.IO) {
            jsonAdapter.toJson(authInfo.fromDomain())
        }
        return localDataStore.set(key = AUTH_INFO_KEY, value = authInfoString)
    }

    companion object {
        private const val AUTH_INFO_KEY = "auth_info"
    }
}