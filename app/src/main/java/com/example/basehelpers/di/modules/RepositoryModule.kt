package com.example.basehelpers.di.modules

import com.example.basehelpers.data.remote.MainService
import com.example.basehelpers.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun bindMainRepository(
        apiService: MainService,
        coroutineDispatcher: CoroutineDispatcher
    ): MainRepository {
        return MainRepository(apiService, coroutineDispatcher)
    }
}