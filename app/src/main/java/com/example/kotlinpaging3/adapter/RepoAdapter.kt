package com.example.kotlinpaging3.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpaging3.R
import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.data.model.UiModel
import java.lang.UnsupportedOperationException

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] which binds [Repo].
 */
class RepoAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(UiModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.list_item_repo) {
            RepoViewHolder.create(parent)
        } else {
            SeparatorViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.RepoItem -> R.layout.list_item_repo
            is UiModel.SeparatorItem -> R.layout.list_item_separator
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (it) {
                is UiModel.RepoItem -> (holder as RepoViewHolder).bind(it.repo)
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(it.label)
            }
        }
    }
}

private class UiModelDiffCallback : DiffUtil.ItemCallback<UiModel>() {

    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return (
            oldItem is UiModel.RepoItem && newItem is UiModel.RepoItem &&
                oldItem.repo.id == newItem.repo.id
            ) || (
            oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                oldItem.label == newItem.label
            )
    }

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem == newItem
    }
}
