package ru.tumist.surfgallery.domain.model

sealed interface ResultModel<out T> {
    data class Success<out T>(val value: T) : ResultModel<T>
    object Error : ResultError
}

sealed interface ResultError : ResultModel<Nothing> {
    object ServiceUnavailable : ResultError
    object InvalidInput : ResultError
    object UnknownError : ResultError
    object ConnectionError : ResultError
    object UnauthorizedError : ResultError
}