package com.mok.moviespaginatedlist.utils.paginationUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.mok.moviespaginatedlist.models.Result
import com.mok.moviespaginatedlist.utils.NetworkState

/**
 * encapsulated all methods for paginate in this class
 **/
interface Listing<T> {

    fun getBoundaryCallback(): LiveData<GenericBoundaryCallback<T>>
    fun getDataSource(): LiveData<PagedList<Result>>
    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap(getBoundaryCallback()) { it.networkState }
}