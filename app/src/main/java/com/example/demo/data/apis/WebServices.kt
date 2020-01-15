package com.example.demo.data.apis

import com.example.demo.data.models.LanguageApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {


    @GET("developers")
    fun getPackage(
        @Query("language") language: String,
        @Query("since") since: String
    ): Deferred<Response<List<LanguageApiResponse>>>


}