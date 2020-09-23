package com.example.kotlinpaging3.data.repository

import androidx.paging.PagingSource
import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.data.network.GithubService
import com.example.kotlinpaging3.data.network.IN_QUALIFIER
import java.io.IOException
import retrofit2.HttpException
import timber.log.Timber

/**
 * [PagingSource] implementation to retrieve data from [GithubService].
 */
class GithubPagingSource(
    private val service: GithubService,
    private val query: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER

        return try {
            val response = service.searchRepos(apiQuery, position, params.loadSize)
            Timber.d("page: $position, response: $response")
            val repos = response.items
            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}

private const val STARTING_PAGE_INDEX = 1
