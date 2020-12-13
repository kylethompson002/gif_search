package com.kylethompson.gifsearch.ui

import androidx.lifecycle.ViewModel
import com.kylethompson.gifsearch.api.TenorApiService

class GifListViewModel(
    val gifApiService: TenorApiService
) : ViewModel()