package ru.tumist.surfgallery.presentation.splash.state

sealed interface SplashScreenState {
    object Loading : SplashScreenState
    object Authorized : SplashScreenState
    object Unauthorized : SplashScreenState
}