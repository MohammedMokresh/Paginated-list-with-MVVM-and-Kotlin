package com.mok.moviespaginatedlist.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "movie",
    indices = [Index("id")]
)
data class Result(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int?,
    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    val originalLanguage: String?,
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    val originalTitle: String?,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String?,
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    val popularity: Double?,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String?,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String?,
    @ColumnInfo(name = "video")
    @SerializedName("video")
    val video: Boolean?,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val voteAverage: Int?,
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    val voteCount: Int?
)