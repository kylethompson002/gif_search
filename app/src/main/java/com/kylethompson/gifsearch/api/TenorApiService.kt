package com.kylethompson.gifsearch.api

import com.kylethompson.gifsearch.api.model.GifPage
import retrofit2.http.GET
import retrofit2.http.Query

interface TenorApiService {

    @GET("/v1/search")
    suspend fun getGifs(
        @Query("q") searchTerm: String,
        @Query("limit") limit: Int,
        @Query("pos") pos: String?,
        @Query("key") key: String = "7C88DVA6AFII", //TODO store in Build.gradle
        @Query("contentFilter") contentFilter: String = "medium",
        @Query("media_filter") mediaFilter: String = "minimal"
    ): GifPage
}