package com.example.kotlinpaging3.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpaging3.databinding.ViewItemRepoLoadStateFooterBinding

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] to display items based on [LoadState].
 */
class RepoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RepoLoadStateAdapter.RepoLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RepoLoadStateViewHolder {
        val binding = ViewItemRepoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RepoLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    class RepoLoadStateViewHolder(private val binding: ViewItemRepoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
}
