package ru.tumist.surfgallery.domain.useCase

import ru.tumist.surfgallery.domain.model.AuthInfo
import ru.tumist.surfgallery.domain.model.ResultModel
import ru.tumist.surfgallery.domain.repository.AuthInfoRepository
import ru.tumist.surfgallery.domain.repository.AuthenticationRepository

class AuthenticateUserUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val authInfoRepository: AuthInfoRepository,
) {
    suspend fun execute(phone: String, password: String): ResultModel<AuthInfo> {
        val result =  authenticationRepository.authenticate(phone, password)
        if (result is ResultModel.Success) {
            authInfoRepository.saveAuthInfo(result.value)
        }
        return result
    }
}