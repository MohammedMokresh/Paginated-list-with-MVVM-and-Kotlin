package com.mok.moviespaginatedlist.languages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mok.moviespaginatedlist.languages.data.LanguagesRepository
import com.mok.moviespaginatedlist.languages.models.LanguagesResponseBody

class LanguagesViewModel(
    private val languagesRepository: LanguagesRepository
) : ViewModel() {


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