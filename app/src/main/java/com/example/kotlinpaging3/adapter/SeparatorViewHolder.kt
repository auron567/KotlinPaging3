package com.example.kotlinpaging3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpaging3.data.model.UiModel
import com.example.kotlinpaging3.databinding.ListItemSeparatorBinding

/**
 * ViewHolder for [UiModel.SeparatorItem] item.
 */
class SeparatorViewHolder(private val binding: ListItemSeparatorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): SeparatorViewHolder {
            val binding = ListItemSeparatorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return SeparatorViewHolder(binding)
        }
    }

    fun bind(label: String) {
        // Set label
        binding.separatorLabel.text = label
    }
}
