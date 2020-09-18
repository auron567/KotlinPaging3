package com.example.kotlinpaging3.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.data.model.Repos
import com.example.kotlinpaging3.data.model.Result
import com.example.kotlinpaging3.data.repository.GithubRepository
import kotlinx.coroutines.launch

/**
 * The [ViewModel] for fetching a list of [Repo].
 */
class SearchViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    /**
     * The current text (repository) to search.
     */
    private val query = MutableLiveData<String>()

    /**
     * A list of [Repo] that updates based on the current text.
     */
    val repoResults: LiveData<Result<Repos>> = query.switchMap { text ->
        liveData {
            val repos = repository.getSearchResultsFlow(text).asLiveData()
            emitSource(repos)
        }
    }

    /**
     * Search repositories based on [text].
     */
    fun searchRepos(text: String) {
        query.value = text
    }

    /**
     * Trigger a new network request when the user scrolls to the end of the displayed list.
     */
    fun listScrolled(totalItemCount: Int, visibleItemCount: Int, lastVisibleItem: Int) {
        if (visibleItemCount + lastVisibleItem + VISIBLE_THRESHOLD >= totalItemCount) {
            query.value?.let { text ->
                viewModelScope.launch {
                    repository.requestMore(text)
                }
            }
        }
    }
}

private const val VISIBLE_THRESHOLD = 5
