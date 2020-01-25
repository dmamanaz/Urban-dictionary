package com.example.nikeurbandictionary.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UrbanDictionaryAPI {
    @Headers(
        "x-rapidapi-host: $rapidApiHost",
        "x-rapidapi-key: $rapidKey" )
    @GET("define")
    fun getUrbanDefinition(@Query("term") input: String) : Call<UrbanResponse>
}