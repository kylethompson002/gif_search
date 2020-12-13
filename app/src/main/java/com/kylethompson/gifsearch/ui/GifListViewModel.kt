package com.kylethompson.gifsearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kylethompson.gifsearch.api.TenorApiService
import com.kylethompson.gifsearch.api.model.Gif
import com.kylethompson.gifsearch.data.GifPagingSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GifListViewModel(
    private val apiService: TenorApiService
) : ViewModel() {

    val gifPageLiveData = MutableLiveData<PagingData<Gif>>()

    fun search(query: String) {
        viewModelScope.launch {
            getGifPager(query)
                .flow.cachedIn(viewModelScope)
                .collect {
                    gifPageLiveData.value = it
                }
        }
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
