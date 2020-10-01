package com.example.kotlinpaging3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpaging3.databinding.ListItemRepoLoadStateFooterBinding

/**
 * ViewHolder for [LoadState] item.
 */
class RepoLoadStateViewHolder(private val binding: ListItemRepoLoadStateFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): RepoLoadStateViewHolder {
            val binding = ListItemRepoLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return RepoLoadStateViewHolder(binding)
        }
    }

    fun bind(loadState: LoadState, retry: () -> Unit) {
        with(binding) {
            // Set retry button click listener
            retryButton.setOnClickListener {
                retry.invoke()
            }

            // Set error message
            if (loadState is LoadState.Error) {
                errorMessage.text = loadState.error.localizedMessage
            }

            // Set views visibility
            progressBar.isVisible = loadState is LoadState.Loading
            errorMessage.isVisible = loadState !is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
        }
    }
}
