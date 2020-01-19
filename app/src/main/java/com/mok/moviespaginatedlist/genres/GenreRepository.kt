package com.mok.moviespaginatedlist.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mok.moviespaginatedlist.MoviesListService
import com.mok.moviespaginatedlist.cache.GenreDao
import com.mok.moviespaginatedlist.utils.NetworkState
import com.mok.moviespaginatedlist.utils.paginationUtils.PagingRequestHelper
import com.mok.moviespaginatedlist.utils.paginationUtils.createStatusLiveData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

interface GenreRepository {

    fun getAllGenres(): LiveData<List<Genre>>
    fun cleared()
    fun insertGenresInLocal()


    open class GenreRepositoryImpl(
        private val service: MoviesListService,
        private val dao: GenreDao
    ) : GenreRepository {


        private val helper =
            PagingRequestHelper(
                Executors.newSingleThreadExecutor()
            )
        val networkState: MutableLiveData<NetworkState> = helper.createStatusLiveData()

        private val compositeDisposable: CompositeDisposable = CompositeDisposable()


        override fun insertGenresInLocal() {
            networkState.value =
                NetworkState.LOADING
            getGenresList()
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    dao.deleteAllGenres()
                        .andThen(insertGenres(it))
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


        override fun cleared() {
            compositeDisposable.clear()
        }


        override fun getAllGenres(): LiveData<List<Genre>> {
            return dao.genres()
        }

        fun insertGenres(list: List<Genre>): Completable {
            return dao.insertAllGenres(list.map { it })
        }

        fun getGenresList(): Single<List<Genre>> =
            service.getGenresList().map { it }

    }


}