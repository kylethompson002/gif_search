package com.kylethompson.gifsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kylethompson.gifsearch.api.TenorApiService
import com.kylethompson.gifsearch.api.model.Gif
import com.kylethompson.gifsearch.data.GifPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel(
    private val apiService: TenorApiService
) : ViewModel() {

    private val searchInputFlow = MutableStateFlow("")

    val gifPages = searchInputFlow
        .debounce(500L)
        .flatMapLatest { query ->
            getGifPager(query).flow
        }.cachedIn(viewModelScope)

    fun process(event: SearchViewEvent) {
        when (event) {
            is SearchViewEvent.Search -> search(event.query)
        }
    }

    private fun search(query: String) {
        searchInputFlow.value = query
    }

    private fun getGifPager(query: String): Pager<String, Gif> {
        return Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 10)
        ) {
            GifPagingSource(apiService = apiService, query = query)
        }
    }
}
