package com.example.kotlinpaging3.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.databinding.ListItemRepoBinding

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] which binds [Repo].
 */
class RepoAdapter : PagingDataAdapter<Repo, RecyclerView.ViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repo = getItem(position)
        (holder as RepoViewHolder).bind(repo)
    }

    class RepoViewHolder(private val binding: ListItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo?) {
            if (repo != null) {
                with(binding) {
                    // Set name
                    repoName.text = repo.fullName

                    // Set description
                    var descriptionVisibility = View.GONE
                    repo.description?.let {
                        repoDescription.text = it
                        descriptionVisibility = View.VISIBLE
                    }
                    repoDescription.visibility = descriptionVisibility

                    // Set stars and forks
                    repoStars.text = "%,d".format(repo.stars)
                    repoForks.text = "%,d".format(repo.forks)
                }
            }
        }
    }
}

private class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {

    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }
}
