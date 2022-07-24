package ru.tumist.surfgallery.service.di

import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.tumist.surfgallery.data.local.LocalDataStore
import ru.tumist.surfgallery.data.respository.AuthInfoDataRepository
import ru.tumist.surfgallery.data.respository.AuthenticationDataRepository
import ru.tumist.surfgallery.data.respository.FavoritesDataRepository
import ru.tumist.surfgallery.data.respository.PicturesDataRepository
import ru.tumist.surfgallery.domain.repository.AuthInfoRepository
import ru.tumist.surfgallery.domain.repository.AuthenticationRepository
import ru.tumist.surfgallery.domain.repository.FavoritesRepository
import ru.tumist.surfgallery.domain.repository.PicturesRepository

val repositoryModule = module {
    single<Moshi> { Moshi.Builder().build() }

    factory<AuthInfoRepository> { AuthInfoDataRepository(get(), get()) }
    factory<AuthenticationRepository> { AuthenticationDataRepository(get()) }
    factory<FavoritesRepository> { FavoritesDataRepository(get(), get()) }
    factory<PicturesRepository> { PicturesDataRepository(get()) }
}