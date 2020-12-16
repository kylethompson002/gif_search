package com.kylethompson.gifsearch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kylethompson.gifsearch.api.model.Gif
import com.kylethompson.gifsearch.api.model.GifDiffCallback
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
        Glide.with(itemView.context)
            .load(gif.media.first().tinyGif.previewUrl)
            .into(binding.image)

        binding.title.text = gif.title
    }
}