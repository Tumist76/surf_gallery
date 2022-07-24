package ru.tumist.surfgallery.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.tumist.surfgallery.data.model.AuthInfoDto
import ru.tumist.surfgallery.data.model.request.AuthRequest

interface AuthApi {
    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest) : AuthInfoDto
}