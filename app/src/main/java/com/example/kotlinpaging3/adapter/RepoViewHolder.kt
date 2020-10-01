package com.example.kotlinpaging3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpaging3.data.model.Repo
import com.example.kotlinpaging3.databinding.ListItemRepoBinding

/**
 * ViewHolder for [Repo] item.
 */
class RepoViewHolder(private val binding: ListItemRepoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val binding = ListItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return RepoViewHolder(binding)
        }
    }

    fun bind(repo: Repo) {
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
