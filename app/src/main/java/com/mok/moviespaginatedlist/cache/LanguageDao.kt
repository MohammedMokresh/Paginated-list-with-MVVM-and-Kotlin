package com.mok.moviespaginatedlist.cache

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mok.moviespaginatedlist.languages.LanguagesResponseBody
import io.reactivex.Completable


@Dao
interface LanguageDao {

    @Query("SELECT * FROM language")
    fun language(): LiveData<List<LanguagesResponseBody>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLanguages(list: List<LanguagesResponseBody>): Completable

    @Query("DELETE FROM language")
    fun deleteAllLanguages(): Completable

}