package com.example.basehelpers.di.modules

import com.example.basehelpers.data.remote.MainService
import com.example.basehelpers.data.repository.SomeDiffRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class MainModule {

    @Provides
    fun bindSomeDiffRepo(
        mainService: MainService,
        coroutineDispatcher: CoroutineDispatcher
    ): SomeDiffRepo {
        return SomeDiffRepo(mainService, coroutineDispatcher)
    }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}