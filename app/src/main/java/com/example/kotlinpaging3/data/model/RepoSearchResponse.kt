package com.example.kotlinpaging3.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents a repository search response from GitHub.
 */
data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int,
    @SerializedName("items") val items: List<Repo>
)
