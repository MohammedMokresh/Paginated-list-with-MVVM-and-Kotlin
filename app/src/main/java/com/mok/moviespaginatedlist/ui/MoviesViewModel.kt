package com.mok.moviespaginatedlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mok.moviespaginatedlist.models.Result
import com.mok.moviespaginatedlist.repository.MovieRepository
import com.mok.moviespaginatedlist.utils.liveData
import com.mok.moviespaginatedlist.utils.paginationUtils.GenericBoundaryCallback
import com.mok.moviespaginatedlist.utils.paginationUtils.Listing

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {


    private val listing: LiveData<Listing<Result>> by lazy {
        liveData(movieRepository.getListable())
    }

    private val boundaryCallback = Transformations.switchMap(listing) { it.getBoundaryCallback() }
    val dataSource = Transformations.switchMap(listing) { it.getDataSource() }
    val networkState = Transformations.switchMap(listing) { it.getNetworkState() }


    fun refreshPage():LiveData<GenericBoundaryCallback<Result>> {

        return Transformations.switchMap(listing) { it.getBoundaryCallback()}
//        boundaryCallback.value?.refreshPage()
    }

    fun deleteAllExceptfirstTen() {
        movieRepository.deleteAllExceptfirstTen()
    }

    /**
     * Retry all failed petitions boundary callback
     */
    fun retry() {
        boundaryCallback.value?.retryPetitions()
    }

    /**
     * Cleared all references and petitions boundary callback
     */
    override fun onCleared() {
        boundaryCallback.value?.cleared()
    }
}