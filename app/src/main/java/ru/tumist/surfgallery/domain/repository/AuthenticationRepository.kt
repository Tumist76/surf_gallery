package ru.tumist.surfgallery.domain.repository

import ru.tumist.surfgallery.domain.model.AuthInfo
import ru.tumist.surfgallery.domain.model.ResultModel

interface AuthenticationRepository {
    suspend fun authenticate(phone: String, password: String): ResultModel<AuthInfo>
}