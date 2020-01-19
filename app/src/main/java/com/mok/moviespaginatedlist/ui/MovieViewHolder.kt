package com.mok.moviespaginatedlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.mok.moviespaginatedlist.R
import com.mok.moviespaginatedlist.databinding.MovieItemBinding
import com.mok.moviespaginatedlist.models.Result
import com.mok.moviespaginatedlist.utils.FragmentSwitcher
import com.mok.moviespaginatedlist.utils.ImageUtil

class MovieViewHolder(itemBinding: MovieItemBinding, fragmentManager: FragmentManager) :
    RecyclerView.ViewHolder(itemBinding.root) {

    companion object {
        fun create(parent: ViewGroup, fragmentManager: FragmentManager): MovieViewHolder {

            val binding: MovieItemBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.movie_item,
                    parent,
                    false
                )
            return MovieViewHolder(binding, fragmentManager)
        }
    }

    var binding: MovieItemBinding = itemBinding
    var fragmentManager: FragmentManager = fragmentManager

    fun bindTo(result: Result?) {


        result?.let {
                ImageUtil.renderImage(
                    result.posterPath, binding.moviePosterImageView
                    , R.drawable.image_not_found
                )

            if (result.title != null)
                binding.titleTextView.text = result.title

            if (result.popularity != null)
                binding.popularityTextView.text = result.popularity.toString()


            binding.root.setOnClickListener {
                if (result.id != null)
                    FragmentSwitcher.addFragment(
                        fragmentManager,
                        R.id.details_FrameLayout,
                        MovieDetailsFragment.newInstance(result.id)
                    )

            }

        }


    }


}