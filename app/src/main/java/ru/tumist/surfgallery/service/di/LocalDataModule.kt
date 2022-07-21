package ru.tumist.surfgallery.service.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.tumist.surfgallery.data.local.LocalDataStore

val localDataModule = module {
    single { LocalDataStore(androidContext()) }
}