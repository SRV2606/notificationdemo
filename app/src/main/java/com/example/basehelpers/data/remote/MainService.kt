package com.example.basehelpers.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface MainService {
    @GET("/about")
    suspend fun getAboutGoogle(): Response<Unit>

}