package com.kylethompson.gifsearch.ui.search

sealed class SearchViewEvent {
    data class Search(val query: String) : SearchViewEvent()
}