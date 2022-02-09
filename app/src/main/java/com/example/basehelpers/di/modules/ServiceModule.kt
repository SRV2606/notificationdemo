package com.example.basehelpers.di.modules

import com.example.basehelpers.data.remote.MainService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    fun provideService(@com.example.basehelpers.ui.Retrofit retrofit: Retrofit): MainService {
        return retrofit.create(MainService::class.java)
    }

    @Provides
    @com.example.basehelpers.ui.Retrofit
    fun retrofit(
        @com.example.basehelpers.ui.OkHttpClient okHttpClient: OkHttpClient,
        @com.example.basehelpers.ui.Gson gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl("https://www.google.com/")
            .build()
    }

    @Provides
    @com.example.basehelpers.ui.Gson
    fun gson(): Gson {
        return GsonBuilder().create()
    }

}