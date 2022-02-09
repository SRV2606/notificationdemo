package com.example.basehelpers.data.repository

import com.example.basehelpers.data.remote.MainService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SomeDiffRepo @Inject constructor(
    private val api: MainService,
    private val coroutineDispatcher: CoroutineDispatcher
) {

}