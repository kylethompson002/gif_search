package com.kylethompson.gifsearch.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Media(

    @Json(name = "tinygif")
    val tinyGif: MediaItem,

    val gif: MediaItem,

    val mp4: MediaItem
)