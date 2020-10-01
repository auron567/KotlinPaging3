package com.example.kotlinpaging3.data.model

/**
 * UI model to support both repositories and separators.
 */
sealed class UiModel {

    data class RepoItem(val repo: Repo) : UiModel()

    data class SeparatorItem(val label: String) : UiModel()
}
