package com.kylethompson.gifsearch.data

import androidx.paging.PagingSource
import com.kylethompson.gifsearch.api.TenorApiService
import com.kylethompson.gifsearch.api.model.Gif
import timber.log.Timber

class GifPagingSource(
    private val apiService: TenorApiService,
    private val query: String,
    private val pageSize: Int = 10
) : PagingSource<String, Gif>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Gif> {
        try {
            val response = apiService.getGifs(
                searchTerm = query,
                limit = pageSize,
                pos = params.key
            )

            return LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = response.next
            )

        } catch (e: Exception) {
            Timber.e(e, "Failed to load gif page!")
            return LoadResult.Error(e)
        }
    }
}
