package com.kylethompson.gifsearch.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GifPage(
    val results: List<ResultItem>,
    val next: String
)