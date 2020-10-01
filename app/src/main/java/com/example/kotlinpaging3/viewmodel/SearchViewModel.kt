package com.example.kotlinpaging3.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.data.model.UiModel
import com.example.kotlinpaging3.data.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * The [ViewModel] for fetching a list of [Repo].
 */
class SearchViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    /**
     * A list of [Repo] that updates based on the current text.
     */
    private var repoResults: Flow<PagingData<UiModel>>? = null

    /**
     * The current text (repository) to search.
     */
    var currentQuery: String? = null
        private set

    /**
     * Search repositories based on [query] string.
     */
    fun searchRepos(query: String): Flow<PagingData<UiModel>> {
        // New search query is the same as the current query
        val lastResult = repoResults
        if (currentQuery == query && lastResult != null) {
            return lastResult
        }

        // New search query is different
        currentQuery = query
        val newResult = repository.getSearchResultsFlow(query)
            .map { pagingData -> pagingData.map { UiModel.RepoItem(it) } }
            .map {
                it.insertSeparators { before, after ->
                    // End of the list
                    if (after == null) {
                        return@insertSeparators null
                    }

                    // Beginning of the list
                    if (before == null) {
                        val label = makeSeparatorLabel(after)
                        return@insertSeparators UiModel.SeparatorItem(label)
                    }

                    // Check between two items
                    if (before.roundedStars > after.roundedStars) {
                        val label = makeSeparatorLabel(after)
                        UiModel.SeparatorItem(label)
                    } else {
                        null
                    }
                }
            }
            .cachedIn(viewModelScope)
        repoResults = newResult
        return newResult
    }

    /**
     * Make label for list separators.
     */
    private fun makeSeparatorLabel(repoItem: UiModel.RepoItem): String {
        return if (repoItem.roundedStars >= 1) {
            "${repoItem.roundedStars}0.000+ stars"
        } else {
            "< 10.000 stars"
        }
    }
}

private val UiModel.RepoItem.roundedStars: Int
    get() = repo.stars / STARS_DIVIDER

private const val STARS_DIVIDER = 10_000
