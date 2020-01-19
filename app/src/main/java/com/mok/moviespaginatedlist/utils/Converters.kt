package com.mok.moviespaginatedlist.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromString(value: String?): List<Int>? {
        val listType =
            object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson<List<Int>>(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}