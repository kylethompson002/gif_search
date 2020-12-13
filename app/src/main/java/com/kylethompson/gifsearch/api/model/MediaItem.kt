package com.kylethompson.gifsearch.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaItem(
    val url: String,

    @Json(name = "dims")
    val dimensions: List<Int>,

    @Json(name = "preview")
    val previewUrl: String
)