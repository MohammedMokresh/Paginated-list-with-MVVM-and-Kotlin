package com.mok.moviespaginatedlist.utils.paginationUtils

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.mok.moviespaginatedlist.models.Result
import com.mok.moviespaginatedlist.utils.NetworkState
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Executors

class GenericBoundaryCallback<T>(
    private val removeAllItems: () -> Completable,
    private val getPage: (page: Int) -> Single<List<T>>,
    private val insertAllItems: (items: List<T>) -> Completable
) : PagedList.BoundaryCallback<Result>() {

    private val helper =
        PagingRequestHelper(
            Executors.newSingleThreadExecutor()
        )
    val networkState: MutableLiveData<NetworkState> = helper.createStatusLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var offsetCount = 1

    fun refreshPage() {
        networkState.value =
            NetworkState.LOADING
        getPage(1)
            .subscribeOn(Schedulers.io())
            .flatMapCompletable {
                removeAllItems()
                    .andThen(insertAllItems(it))
            }.subscribeBy(
                onComplete = {
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = {
                    networkState.value =
                        NetworkState.error(
                            it.message
                        )
                }
            ).addTo(compositeDisposable)
    }

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            getTop(offsetCount, it)
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Result) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            getTop(offsetCount, it)
        }
    }


    override fun onItemAtFrontLoaded(itemAtFront: Result) {
        // ignored, since we only ever append to what's in the DB
    }


    private fun getTop(offset: Int, pagingRequest: PagingRequestHelper.Request.Callback) {
        Timber.d("Request a new page $offset")
        getPage(offset)
            .subscribeOn(Schedulers.io())
            .flatMapCompletable {
                insertAllItems(it)
            }.subscribeBy(
                onComplete = {
                    pagingRequest.recordSuccess()
                    offsetCount += 1
                },
                onError = {
                    networkState.postValue(
                        NetworkState.error(
                            it.message
                        )
                    )
                    Timber.e(it, "Error when getTop with page $offset")
                    pagingRequest.recordFailure(it)
                }
            ).addTo(compositeDisposable)
    }


    /** Clear all references **/
    fun cleared() {
        compositeDisposable.clear()
    }

    fun retryPetitions() = helper.retryAllFailed()


}
