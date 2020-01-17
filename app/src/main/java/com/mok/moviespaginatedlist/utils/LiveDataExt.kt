package com.mok.moviespaginatedlist.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


/***
 * Create and initialize livedata
 * */
fun <T> liveData(data:T): LiveData<T> {
    val mld = MutableLiveData<T>()
    mld.value = data
    return mld
}
