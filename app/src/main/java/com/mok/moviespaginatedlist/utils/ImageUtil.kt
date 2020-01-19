package com.mok.moviespaginatedlist.utils

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mok.moviespaginatedlist.BuildConfig


object ImageUtil {


    fun renderImage(photoUrl: String?, imageView: ImageView, placeholder: Int) {
        Log.e("adasd", photoUrl+"aaaaa")
        if (photoUrl == "" || photoUrl == null) {
            Glide.with(imageView.context)
                .load(placeholder)
                .fitCenter().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        } else {
            Glide.with(imageView.context)
                .load(BuildConfig.SMALL_IMAGE_PREFIX + photoUrl)
                .placeholder(placeholder)
                .error(placeholder)
                .fitCenter().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }

    }


}