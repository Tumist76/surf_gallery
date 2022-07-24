package ru.tumist.surfgallery.service.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.tumist.surfgallery.BuildConfig
import ru.tumist.surfgallery.data.network.AuthApi
import ru.tumist.surfgallery.service.ApplicationState


class RequestInterceptor(
    private val onUnauthenticated: () -> Unit,
    private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        val response = chain.proceed(request)
        when (response.code) {
            401 -> {
                onUnauthenticated()
            }
        }
        return response
    }
}

const val BASE_URL = "https://pictures.chronicker.fun/api/"

val networkModule = module {
    fun createOkhttpClient(): OkHttpClient {
        val appState: ApplicationState by inject(ApplicationState::class.java)
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val token = appState.authInfo?.token ?: ""
        val requestInterceptor = RequestInterceptor(appState.onUnauthenticated, token)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
    }

    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    fun createAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    single { createOkhttpClient() }
    single { createRetrofit(get()) }
    single { createAuthApi(get()) }
}