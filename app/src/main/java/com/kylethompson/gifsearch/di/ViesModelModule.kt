package com.kylethompson.gifsearch.di

import com.kylethompson.gifsearch.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
}
