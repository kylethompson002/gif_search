package com.kylethompson.gifsearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import com.kylethompson.gifsearch.databinding.FragmentSearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private val gifAdapter = SearchAdapter()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchEditText.doAfterTextChanged { text ->
            viewModel.search(text?.toString() ?: "")
        }

        binding.retryButton.setOnClickListener { gifAdapter.retry() }

        binding.gifRecycler.apply {
            layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL).apply {
                    gapStrategy = GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                }
            adapter = gifAdapter.withLoadStateHeaderAndFooter(
                header = GifsLoadStateAdapter { gifAdapter.retry() },
                footer = GifsLoadStateAdapter { gifAdapter.retry() }
            )
        }

        viewModel.searchResults.observe(viewLifecycleOwner) {
            gifAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        gifAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.gifRecycler.isVisible = loadState.source.refresh !is LoadState.Error
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    view.context,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}