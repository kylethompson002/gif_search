package com.kylethompson.gifsearch.ui.search

import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(
    private val apiService: TenorApiService
) : ViewModel() {

    private val _searchInput = MutableStateFlow("")
    private val _searchResults = MutableLiveData<PagingData<Gif>>()
    val searchResults: LiveData<PagingData<Gif>> get() = _searchResults

    init {
        viewModelScope.launch {
            _searchInput
                .debounce(500)
                .flatMapLatest { getSearchResultStream(it) }
                .cachedIn(viewModelScope)
                .collectLatest {
                    _searchResults.postValue(it)
                }
        }
    }

    fun search(query: String) {
        _searchInput.value = query
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
