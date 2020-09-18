package com.example.kotlinpaging3.data.repository

import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.data.model.Repos
import com.example.kotlinpaging3.data.model.Result
import com.example.kotlinpaging3.data.network.GithubService
import com.example.kotlinpaging3.data.network.IN_QUALIFIER
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import timber.log.Timber

/**
 * Repository module for handling [Repo] data operations.
 */
class GithubRepository @Inject constructor(
    private val service: GithubService
) {

    /**
     * List of all results received.
     */
    private val inMemoryCache = mutableListOf<Repo>()

    /**
     * Channel of results. The subscriber will always have the most recent data.
     */
    private val searchResults = ConflatedBroadcastChannel<Result<Repos>>()

    /**
     * Current page to request.
     */
    private var currentPage = STARTING_PAGE_INDEX

    /**
     * Avoid triggering multiple requests in the same time.
     */
    private var isRequestInProgress = false

    /**
     * Search repositories whose names match [query] string, exposed as a stream of data.
     */
    suspend fun getSearchResultsFlow(query: String): Flow<Result<Repos>> {
        Timber.d("new query: $query")
        currentPage = STARTING_PAGE_INDEX
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults.asFlow()
    }

    /**
     * Trigger a new network request with [query] string.
     */
    suspend fun requestMore(query: String) {
        if (!isRequestInProgress) {
            requestAndSaveData(query)
        }
    }

    /**
     * Make a network request with [query] string.
     *
     * When the request is successful, increment the page number.
     */
    private suspend fun requestAndSaveData(query: String) {
        // Request started
        isRequestInProgress = true

        try {
            val apiQuery = query + IN_QUALIFIER
            val response = service.searchRepos(apiQuery, currentPage, NETWORK_PAGE_SIZE)
            Timber.d("page: $currentPage, response: $response")
            val repos = response.items
            inMemoryCache.addAll(repos)
            searchResults.offer(Result.success(inMemoryCache))

            // Request is successful, increment the page number
            currentPage++
        } catch (exception: IOException) {
            searchResults.offer(Result.error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(Result.error(exception))
        }

        // Request finished
        isRequestInProgress = false
    }
}

private const val STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 30
