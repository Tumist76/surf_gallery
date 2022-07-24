package ru.tumist.surfgallery.service.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.tumist.surfgallery.data.local.LocalDataStore
import ru.tumist.surfgallery.domain.useCase.AuthenticateUserUseCase
import ru.tumist.surfgallery.domain.useCase.ChangeFavoriteStatusUseCase
import ru.tumist.surfgallery.domain.useCase.ClearLocalDataUseCase
import ru.tumist.surfgallery.domain.useCase.GetPicturesUseCase
import ru.tumist.surfgallery.presentation.auth.viewModel.AuthScreenViewModel
import ru.tumist.surfgallery.presentation.splash.viewModel.SplashScreenViewModel

val useCaseModule = module {
    factory { AuthenticateUserUseCase(get(), get()) }
    factory { GetPicturesUseCase(get(), get()) }
    factory { ChangeFavoriteStatusUseCase(get()) }
    factory { ClearLocalDataUseCase(get(), get()) }
}