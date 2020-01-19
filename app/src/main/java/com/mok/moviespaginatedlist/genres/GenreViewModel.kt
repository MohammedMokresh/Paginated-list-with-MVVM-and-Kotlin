package com.mok.moviespaginatedlist.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class GenreViewModel(
    private val genreRepository: GenreRepository
) : ViewModel() {


    fun getAllGenres(): LiveData<List<Genre>> {
        return genreRepository.getAllGenres()
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