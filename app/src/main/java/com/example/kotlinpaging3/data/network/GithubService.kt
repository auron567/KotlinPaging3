package com.example.kotlinpaging3.data.network

import com.example.kotlinpaging3.data.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to connect to the GitHub API to fetch repositories.
 */
interface GithubService {

    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponse
}

const val IN_QUALIFIER = "in:name,description"
