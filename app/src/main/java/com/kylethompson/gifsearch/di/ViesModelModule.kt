package com.kylethompson.gifsearch.di

import com.kylethompson.gifsearch.ui.GifListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GifListViewModel(get()) }
}
