package ru.tumist.surfgallery.presentation.auth.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.tumist.surfgallery.domain.model.ResultError
import ru.tumist.surfgallery.domain.model.ResultModel
import ru.tumist.surfgallery.domain.useCase.AuthenticateUserUseCase
import ru.tumist.surfgallery.presentation.auth.state.AuthErrorType
import ru.tumist.surfgallery.presentation.auth.state.AuthScreenState
import ru.tumist.surfgallery.presentation.auth.state.FieldValidationErrorType
import ru.tumist.surfgallery.service.ApplicationState

class AuthScreenViewModel(
    private val authenticateUseCase: AuthenticateUserUseCase,
    private val applicationState: ApplicationState,
) : ViewModel() {
    val state = MutableStateFlow<AuthScreenState>(AuthScreenState.Idle)

    private var phone: String = ""
    private var password: String = ""

    fun setPhone(value: String) {
        phone = value
    }

    fun setPassword(value: String) {
        password = value
    }

    fun authenticate() {
        if (!isInputValid()) return
        getToken()
    }

    private fun getToken() {

        viewModelScope.launch {
            state.emit(AuthScreenState.Loading)
            val processedPhone = "+7$phone"
            when (val result = authenticateUseCase.execute(processedPhone, password)) {
                is ResultModel.Success -> {
                    applicationState.authInfo = result.value
                    state.emit(AuthScreenState.Authorized)
                }
                ResultError.ConnectionError -> state.emit(
                    AuthScreenState.AuthError(AuthErrorType.CONNECTION_ERROR)
                )
                else -> state.emit(AuthScreenState.AuthError(AuthErrorType.CREDENTIALS_INVALID))
            }
        }
    }

    private fun isInputValid(): Boolean {
        val phoneValidationError = getPhoneValidationError(phone)
        val passwordValidationError = getPasswordValidationError(password)
        if (phoneValidationError != null || passwordValidationError != null) {
            viewModelScope.launch {
                state.emit(
                    AuthScreenState.InvalidEntry(
                        phoneValidationError,
                        passwordValidationError
                    )
                )
            }
            return false
        }
        return true
    }

    private fun getPhoneValidationError(phone: String): FieldValidationErrorType? {
        return when {
            phone.isEmpty() -> FieldValidationErrorType.EMPTY
            phone.length != 10 -> FieldValidationErrorType.INVALID
            else -> null
        }
    }

    private fun getPasswordValidationError(password: String): FieldValidationErrorType? {
        return when {
            password.isEmpty() -> FieldValidationErrorType.EMPTY
            password.length !in 6..255 -> FieldValidationErrorType.INVALID
            else -> null
        }
    }
}