package com.mok.moviespaginatedlist.cache

import androidx.paging.DataSource
import androidx.room.*
import com.mok.moviespaginatedlist.models.Result
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface MovieDao {


    @Query("SELECT * FROM movie")
    fun movies(): Single<List<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Result): Completable

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>): Completable

    @Query("SELECT * FROM movie ORDER BY release_date DESC")
    fun getAllPaged(): DataSource.Factory<Int, Result>

    @Query("DELETE FROM movie")
    fun deleteAll(): Completable


    @Query("DELETE FROM movie WHERE id NOT IN (SELECT id from movie limit 10)")
    fun deleteAllExceptfirstTen()
}