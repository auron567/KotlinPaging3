package com.example.kotlinpaging3.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.data.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

/**
 * The [ViewModel] for fetching a list of [Repo].
 */
class SearchViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    /**
     * A list of [Repo] that updates based on the current text.
     */
    private var repoResults: Flow<PagingData<Repo>>? = null

    /**
     * The current text (repository) to search.
     */
    var currentQuery: String? = null
        private set

    /**
     * Search repositories based on [query] string.
     */
    fun searchRepos(query: String): Flow<PagingData<Repo>> {
        // New search query is the same as the current query
        val lastResult = repoResults
        if (currentQuery == query && lastResult != null) {
            return lastResult
        }

        // New search query is different
        currentQuery = query
        val newResult = repository.getSearchResultsFlow(query)
            .cachedIn(viewModelScope)
        repoResults = newResult
        return newResult
    }
}
