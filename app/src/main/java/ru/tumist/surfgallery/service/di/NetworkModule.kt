package ru.tumist.surfgallery.service.di

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.tumist.surfgallery.data.network.AuthApi
import ru.tumist.surfgallery.data.network.PicturesApi
import ru.tumist.surfgallery.data.utils.EpochDateJsonAdapter
import ru.tumist.surfgallery.service.ApplicationState


class AppStateTokenRequestInterceptor(
    private val applicationState: ApplicationState
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = applicationState.authInfo?.token ?: ""
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Token $token")
            .build()
        val response = chain.proceed(request)
        when (response.code) {
            401 -> {
                applicationState.onUnauthenticated()
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
        val requestInterceptor = AppStateTokenRequestInterceptor(appState)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
    }

    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().add(EpochDateJsonAdapter()).build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    fun createAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    fun createPicturesApi(retrofit: Retrofit): PicturesApi {
        return retrofit.create(PicturesApi::class.java)
    }

    single { createOkhttpClient() }
    single { createRetrofit(get()) }
    single { createAuthApi(get()) }
    single { createPicturesApi(get()) }
}