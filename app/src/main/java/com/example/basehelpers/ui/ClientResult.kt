package com.example.basehelpers.ui

import com.example.basehelpers.data.model.SampleModel

sealed class ClientResult<out T> {
    data class Success<out T>(val data: T) : ClientResult<T>()
    data class Error(val error: SampleModel.ApiError) : ClientResult<Nothing>()
    object InProgress : ClientResult<Nothing>()
}
