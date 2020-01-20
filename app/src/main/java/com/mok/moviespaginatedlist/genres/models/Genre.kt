package com.mok.moviespaginatedlist.genres.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "genre"
)
data class Genre(
    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    @SerializedName("id")
    val id: Int?,

    @ColumnInfo(name = "genre_name")
    @SerializedName("name")
    val name: String?
)