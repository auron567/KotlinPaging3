package com.example.kotlinpaging3.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.data.network.GithubService
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

/**
 * Repository module for handling [Repo] data operations.
 */
class GithubRepository @Inject constructor(
    private val service: GithubService
) {

    /**
     * Search repositories whose names match [query] string, exposed as a stream of data.
     */
    fun getSearchResultsFlow(query: String): Flow<PagingData<Repo>> {
        Timber.d("new query: $query")
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }
}

private const val INITIAL_LOAD_SIZE = 40
private const val NETWORK_PAGE_SIZE = 30
