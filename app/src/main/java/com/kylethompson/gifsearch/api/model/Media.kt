package com.kylethompson.gifsearch.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Media(
    val tinyGif: MediaItem,
    val gif: MediaItem,
    val mp4: MediaItem
)