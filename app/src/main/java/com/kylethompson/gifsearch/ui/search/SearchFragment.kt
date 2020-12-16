package com.kylethompson.gifsearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kylethompson.gifsearch.databinding.FragmentSearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
            search(text?.toString() ?: "")
        }

        binding.gifRecycler.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = gifAdapter
        }
    }

    private var searchJob: Job? = null
    private fun search(query: String) {
        if (query.isNotEmpty()) {
            binding.gifRecycler.scrollToPosition(0)

            // Make sure we cancel the previous job before creating a new one
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                viewModel.searchGifs(query).collectLatest {
                    gifAdapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}