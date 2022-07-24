package ru.tumist.surfgallery.presentation.auth.state

sealed interface AuthScreenState {
    data class InvalidEntry(
        val phoneError: FieldValidationErrorType?,
        val passwordError: FieldValidationErrorType?,
    ) : AuthScreenState
    data class AuthError(val error : AuthErrorType) : AuthScreenState

    object Loading : AuthScreenState
    object Authorized : AuthScreenState
    object Idle : AuthScreenState
}

enum class AuthErrorType {
    CONNECTION_ERROR,
    CREDENTIALS_INVALID
}

enum class FieldValidationErrorType {
    EMPTY,
    INVALID,
}