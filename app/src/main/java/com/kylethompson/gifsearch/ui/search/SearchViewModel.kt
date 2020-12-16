package com.kylethompson.gifsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kylethompson.gifsearch.api.TenorApiService
import com.kylethompson.gifsearch.api.model.Gif
import com.kylethompson.gifsearch.data.GifPagingSource
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val apiService: TenorApiService
) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Gif>>? = null

    fun searchGifs(queryString: String): Flow<PagingData<Gif>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Gif>> = getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    private fun getSearchResultStream(query: String): Flow<PagingData<Gif>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GifPagingSource(apiService = apiService, query = query) }
        ).flow
    }
}
