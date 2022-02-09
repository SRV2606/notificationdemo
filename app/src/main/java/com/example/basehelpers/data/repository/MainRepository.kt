package com.example.basehelpers.data.repository

import com.example.basehelpers.data.remote.MainService
import com.example.basehelpers.ui.ClientResult
import com.example.basehelpers.ui.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: MainService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAboutGoogle(): ClientResult<Unit> {
        return withContext(coroutineDispatcher) {
            return@withContext safeApiCall {
                api.getAboutGoogle()
            }
        }
    }
}