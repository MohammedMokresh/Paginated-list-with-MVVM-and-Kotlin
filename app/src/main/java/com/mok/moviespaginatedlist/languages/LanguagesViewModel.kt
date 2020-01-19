package com.mok.moviespaginatedlist.languages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class LanguagesViewModel(
    private val languagesRepository: LanguagesRepository
) : ViewModel() {


    fun getAllLanguages(): LiveData<List<LanguagesResponseBody>> {
        return languagesRepository.getAllLanguages()
    }

    fun callAndSaveLanguages() {
        return languagesRepository.callAndSaveLanguages()
    }


    /**
     * Cleared all references and petitions boundary callback
     */
    override fun onCleared() {
        languagesRepository.cleared()
    }


}