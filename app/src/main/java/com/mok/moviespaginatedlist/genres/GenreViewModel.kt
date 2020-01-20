package com.mok.moviespaginatedlist.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mok.moviespaginatedlist.genres.data.GenreRepository
import com.mok.moviespaginatedlist.genres.models.Genre

class GenreViewModel(
    private val genreRepository: GenreRepository
) : ViewModel() {


    fun getGenreById(genreId: Int): LiveData<Genre> {
        return genreRepository.getGenreById(genreId)
    }

    fun insertGenresInLocal() {
        return genreRepository.insertGenresInLocal()
    }


    /**
     * Cleared all references and petitions boundary callback
     */
    override fun onCleared() {
        genreRepository.cleared()
    }


}