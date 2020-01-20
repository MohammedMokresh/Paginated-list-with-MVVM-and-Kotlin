package com.mok.moviespaginatedlist.genres.models


import com.google.gson.annotations.SerializedName

data class GenresResponseBody<T>(
    @SerializedName("genres")
    val genres:T
)