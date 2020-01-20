package com.mok.moviespaginatedlist.languages.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "language"
)
data class LanguagesResponseBody(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "language_id")
    val language_id: Int,

    @ColumnInfo(name = "english_name")
    @SerializedName("english_name")
    val englishName: String?,

    @ColumnInfo(name = "iso_639_1")
    @SerializedName("iso_639_1")
    val iso6391: String?,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?
)