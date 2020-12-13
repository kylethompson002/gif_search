package com.kylethompson.gifsearch.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultItem(

    val id: String,

    val title: String,

    @Json(name = "url")
    val tenorUrl: String,

    val itemUrl: String,

    val media: List<Media>
)