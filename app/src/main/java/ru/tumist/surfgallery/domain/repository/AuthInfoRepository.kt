package ru.tumist.surfgallery.domain.repository

import ru.tumist.surfgallery.domain.model.AuthInfo

interface AuthInfoRepository {
    suspend fun getAuthInfo() : AuthInfo?
    suspend fun saveAuthInfo(authInfo : AuthInfo) : Boolean
}