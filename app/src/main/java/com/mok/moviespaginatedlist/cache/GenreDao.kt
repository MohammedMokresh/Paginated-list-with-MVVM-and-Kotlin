package com.mok.moviespaginatedlist.cache

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mok.moviespaginatedlist.genres.Genre
import io.reactivex.Completable


@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    fun genres(): LiveData<List<Genre>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenres(list: List<Genre>): Completable

    @Query("DELETE FROM genre")
    fun deleteAllGenres(): Completable

}