package com.kylethompson.gifsearch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kylethompson.gifsearch.R
import com.kylethompson.gifsearch.databinding.ItemLoadingFooterBinding


class GifsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<GifsLoadingStateViewHolder>() {
    override fun onBindViewHolder(holder: GifsLoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GifsLoadingStateViewHolder {
        return GifsLoadingStateViewHolder.create(parent, retry)
    }
}

class GifsLoadingStateViewHolder(
    private val binding: ItemLoadingFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): GifsLoadingStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading_footer, parent, false)

            view.updateLayoutParams<StaggeredGridLayoutManager.LayoutParams> {
                isFullSpan = true
            }

            val binding = ItemLoadingFooterBinding.bind(view)
            return GifsLoadingStateViewHolder(binding, retry)
        }
    }
}



