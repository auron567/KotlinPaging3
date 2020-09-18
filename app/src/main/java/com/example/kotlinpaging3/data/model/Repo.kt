package com.example.kotlinpaging3.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents a GitHub repository.
 */
data class Repo(
    @SerializedName("id") val id: Long,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("forks_count") val forks: Int
)

typealias Repos = List<Repo>
