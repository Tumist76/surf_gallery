package ru.tumist.surfgallery.data.respository

import ru.tumist.surfgallery.data.model.request.AuthRequest
import ru.tumist.surfgallery.data.model.toDomain
import ru.tumist.surfgallery.data.network.AuthApi
import ru.tumist.surfgallery.data.utils.makeApiRequest
import ru.tumist.surfgallery.domain.model.AuthInfo
import ru.tumist.surfgallery.domain.model.ResultModel
import ru.tumist.surfgallery.domain.repository.AuthenticationRepository

class AuthenticationDataRepository(private val authApi: AuthApi) : AuthenticationRepository {
    override suspend fun authenticate(phone: String, password: String): ResultModel<AuthInfo> {
        return makeApiRequest {
            authApi.auth(AuthRequest(phone = phone, password = password)).toDomain()
        }
    }
}