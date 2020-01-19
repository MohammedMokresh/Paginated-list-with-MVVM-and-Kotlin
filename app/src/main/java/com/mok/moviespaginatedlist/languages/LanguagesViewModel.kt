package com.mok.moviespaginatedlist.languages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class LanguagesViewModel(
    private val languagesRepository: LanguagesRepository
) : ViewModel() {


    fun getAllLanguages(): LiveData<List<LanguagesResponseBody>> {
        return languagesRepository.getAllLanguages()
    }

    fun getLanguageFromIso(iso: String): LiveData<LanguagesResponseBody> {
        return languagesRepository.getLanguageFromIso(iso)
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