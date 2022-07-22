package ru.tumist.surfgallery.presentation.splash.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.tumist.surfgallery.domain.repository.AuthInfoRepository
import ru.tumist.surfgallery.presentation.splash.state.SplashScreenState
import ru.tumist.surfgallery.service.ApplicationState

class SplashScreenViewModel(
    private val authInfoRepository: AuthInfoRepository,
    private val applicationState: ApplicationState,
) : ViewModel() {
    val state = MutableStateFlow<SplashScreenState>(SplashScreenState.Loading)

    fun getAuthInfo() {
        viewModelScope.launch {
            val authInfo = authInfoRepository.getAuthInfo();
            if (authInfo == null) {
                state.emit(SplashScreenState.Unauthorized)
                return@launch
            };
            applicationState.authInfo = authInfo
            state.emit(SplashScreenState.Authorized)
        }
    }
}