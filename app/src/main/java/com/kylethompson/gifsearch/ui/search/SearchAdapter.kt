package com.kylethompson.gifsearch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kylethompson.gifsearch.R
import com.kylethompson.gifsearch.api.model.Gif
import com.kylethompson.gifsearch.api.model.GifDiffCallback
import com.kylethompson.gifsearch.api.model.MediaItem
import com.kylethompson.gifsearch.databinding.ItemGifBinding

class SearchAdapter : PagingDataAdapter<Gif, GifViewHolder>(GifDiffCallback()) {

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
    }
}

class GifViewHolder(private val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(gif: Gif) {
        bindMediaItem(gif.media.first().gif)
        binding.title.text = gif.title
    }

    private fun bindMediaItem(item: MediaItem) {
        with(binding.image) {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                width = item.dimensions[0]
                height = item.dimensions[1]
            }
            Glide.with(this.context)
                .load(item.url)
                .placeholder(R.drawable.ic_image)
                .into(binding.image)
        }
    }
}