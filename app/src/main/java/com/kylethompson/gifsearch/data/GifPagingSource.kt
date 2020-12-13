package com.kylethompson.gifsearch.data

import androidx.paging.PagingSource
import com.kylethompson.gifsearch.api.TenorApiService
import com.kylethompson.gifsearch.api.model.Gif
import retrofit2.HttpException
import java.io.IOException

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

        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }
}
