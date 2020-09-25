package com.example.kotlinpaging3.view

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.kotlinpaging3.R
import com.example.kotlinpaging3.app.longToast
import com.example.kotlinpaging3.databinding.ActivitySearchBinding
import com.example.kotlinpaging3.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()
    private val repoAdapter = RepoAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setReposRecyclerView()
        setRetryButton()
        initSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        setSearchView(menu)
        return true
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
            adapter = repoAdapter.withLoadStateHeaderAndFooter(
                header = RepoLoadStateAdapter { repoAdapter.retry() },
                footer = RepoLoadStateAdapter { repoAdapter.retry() }
            )

            // Set LoadStateListener
            repoAdapter.addLoadStateListener { loadState ->
                // Set views visibility
                with(binding) {
                    reposRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    retryButton.isVisible = loadState.source.refresh is LoadState.Error
                }

                // Show a toast on error
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    longToast(it.error.localizedMessage)
                }
            }
        }
    }

    /**
     * Retry button configuration.
     */
    private fun setRetryButton() {
        binding.retryButton.setOnClickListener {
            repoAdapter.retry()
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
                    search(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
    }

    /**
     * Initial search configuration.
     */
    private fun initSearch() {
        // Restore search results after configuration changes
        viewModel.currentQuery?.let {
            search(it)
        }

        // Reset scroll position for each new search
        lifecycleScope.launch {
            repoAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.reposRecyclerView.scrollToPosition(0) }
        }
    }

    /**
     * Search for a new [query].
     */
    private fun search(query: String) {
        // Cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepos(query).collectLatest {
                repoAdapter.submitData(it)
            }
        }
    }
}
