package ru.tumist.surfgallery.data.utils

import android.util.Log
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import ru.tumist.surfgallery.domain.model.ResultError
import ru.tumist.surfgallery.domain.model.ResultModel
import java.net.ConnectException
import java.net.UnknownHostException

suspend fun <T> makeApiRequest(
    apiCall: suspend () -> T
): ResultModel<T> = runCatching {
    ResultModel.Success(apiCall())
}.getOrElse { exception ->
    Log.e("ApiCall", exception.toString())
    handleThrowable(exception)
}

private fun parseHttpException(exception: HttpException): ResultModel<Nothing> {
    when (exception.code()) {
        400 -> return ResultError.InvalidInput
        401 -> return ResultError.UnauthorizedError
        in 500..599 -> return ResultError.ServiceUnavailable
    }
    return ResultError.UnknownError
}

private fun handleThrowable(throwable: Throwable): ResultModel<Nothing> = when (throwable) {
    is HttpException -> parseHttpException(throwable)
    is UnknownHostException -> ResultError.ConnectionError
    is ConnectException -> ResultError.ConnectionError
    else -> ResultError.UnknownError
}
