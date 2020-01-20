package com.mok.moviespaginatedlist.moviesList.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.mok.moviespaginatedlist.moviesList.models.Result
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface MovieDao {


    @Query("SELECT * FROM movie")
    fun movies(): Single<List<Result>>


    @Query("SELECT * FROM movie Where id == :movieId ")
    fun getMovieById(movieId: Int): LiveData <Result>


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(list: List<Result>): Completable

    @Query("SELECT * FROM movie ORDER BY release_date DESC")
    fun getAllPaged(): DataSource.Factory<Int, Result>

    @Query("DELETE FROM movie")
    fun deleteAllMovies(): Completable


    @Query("DELETE FROM movie WHERE id NOT IN (SELECT id from movie ORDER BY release_date DESC limit 10)")
    fun deleteAllExceptfirstTen()


}