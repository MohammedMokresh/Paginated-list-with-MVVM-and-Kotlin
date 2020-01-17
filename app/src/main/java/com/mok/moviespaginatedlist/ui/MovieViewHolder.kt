package com.mok.moviespaginatedlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mok.moviespaginatedlist.BuildConfig
import com.mok.moviespaginatedlist.R
import com.mok.moviespaginatedlist.databinding.MovieItemBinding
import com.mok.moviespaginatedlist.models.Result

class MovieViewHolder(itemBinding: MovieItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {

            val binding: MovieItemBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.movie_item,
                    parent,
                    false
                )
            return MovieViewHolder(binding)
        }
    }

    var binding: MovieItemBinding = itemBinding

    fun bindTo(result: Result?) {

        result?.let {
            if (result.posterPath != null)
                Glide.with(binding.root.context)
                    .load(BuildConfig.SMALL_IMAGE_PREFIX + result.posterPath)
                    .fitCenter().centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.moviePosterImageView)
        }

    }


}