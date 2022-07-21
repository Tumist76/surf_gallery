package ru.tumist.surfgallery.service.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.tumist.surfgallery.data.local.LocalDataStore
import ru.tumist.surfgallery.presentation.splash.viewModel.SplashScreenViewModel

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get(), get()) }
}