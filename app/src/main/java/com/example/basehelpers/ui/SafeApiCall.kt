package com.example.basehelpers.ui

import com.example.basehelpers.data.model.SampleModel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


private const val ERROR_TOKEN_EXPIRED = 403
private const val ERROR_TOKEN_NOT_PROVIDED = 402


suspend fun <T> safeApiCall(call: suspend () -> Response<T>): ClientResult<T> {
    return try {
        val response = call.invoke()
        return if (response.isSuccessful) {
            response.body()?.let {
                createSuccess(it)
            } ?: run {
                createErrorWithResponse(response)
            }
        } else {
            createErrorWithResponse(response)
        }
    } catch (exception: Throwable) {
        createError(exception)
    }
}

fun <T> createSuccess(response: T): ClientResult<T> =
    ClientResult.Success(response)

private fun <T> createErrorWithResponse(response: Response<T>): ClientResult<T> {
    when (response.code()) {
        ERROR_TOKEN_EXPIRED -> {
            fireTokenExpiredEvent()
        }
        ERROR_TOKEN_NOT_PROVIDED -> {
            fireTokenExpiredEvent()
        }
    }
    return try {
        val error: ApiErrorResponse = Gson().fromJson(
            response.errorBody()?.string(),
            ApiErrorResponse::class.java
        )
        ClientResult.Error(SampleModel.ApiError(error.errorMessage.errorMessage))
    } catch (t: Throwable) {
        createError(t)
    }
}

private fun fireTokenExpiredEvent() {
    // EventBus.getDefault().post(TokenExpiredEvent())
}

private fun <T> createError(t: Throwable): ClientResult<T> {
    val defaultMessage = "Internet not working properly"
//        Timber.e(t)
//        FirebaseCrashlytics.getInstance().recordException(t)
    val error = when (t) {
        is SampleModel.NoConnectivityException -> {
            SampleModel.ApiError(defaultMessage)
        }
        is SSLHandshakeException -> {
            SampleModel.ApiError(defaultMessage)
        }
        is UnknownHostException -> {
            SampleModel.ApiError(defaultMessage)
        }
        else -> SampleModel.ApiError(t.localizedMessage)
    }
    return ClientResult.Error(error)
}


internal data class ApiErrorResponse(
    @SerializedName("auth")
    val auth: Boolean = false,
    @SerializedName("error")
    val errorMessage: ErrorMessage = ErrorMessage()
)

data class ErrorMessage(
    @SerializedName("name")
    val errorName: String = "",
    @SerializedName("message")
    val errorMessage: String = ""
)


