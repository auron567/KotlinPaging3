package com.example.kotlinpaging3.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpaging3.R
import com.example.kotlinpaging3.app.longToast
import com.example.kotlinpaging3.data.model.Result
import com.example.kotlinpaging3.databinding.ActivitySearchBinding
import com.example.kotlinpaging3.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()
    private val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRepoResultsObserver()
        setReposRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        setSearchView(menu)

        return true
    }

    /**
     * LiveData observer configuration.
     */
    private fun setRepoResultsObserver() {
        viewModel.repoResults.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    val repos = result.data
                    showEmptyView(repos.isEmpty())
                    repoAdapter.submitList(repos.toMutableList())
                }
                is Result.Error -> {
                    val message = result.exception.message
                    longToast(message)
                }
            }
        }
    }

    /**
     * Show or hide the empty view.
     */
    private fun showEmptyView(show: Boolean) {
        if (show) {
            binding.reposRecyclerView.visibility = View.INVISIBLE
            binding.noDataText.visibility = View.VISIBLE
        } else {
            binding.reposRecyclerView.visibility = View.VISIBLE
            binding.noDataText.visibility = View.GONE
        }
    }

    /**
     * RecyclerView configuration.
     */
    private fun setReposRecyclerView() {
        binding.reposRecyclerView.apply {
            // Add divider between items
            val divider = DividerItemDecoration(this@SearchActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)

            // Set adapter
            adapter = repoAdapter

            // Set OnScrollListener
            val layoutManager = layoutManager as LinearLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = layoutManager.itemCount
                    val visibleItemCount = layoutManager.childCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    viewModel.listScrolled(totalItemCount, visibleItemCount, lastVisibleItem)
                }
            })
        }
    }

    /**
     * SearchView configuration.
     */
    private fun setSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)

        (searchItem.actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.searchRepos(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
    }
}
