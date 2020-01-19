package com.mok.moviespaginatedlist.languages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mok.moviespaginatedlist.MoviesListService
import com.mok.moviespaginatedlist.cache.LanguageDao
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

interface LanguagesRepository {

    fun getAllLanguages(): LiveData<List<LanguagesResponseBody>>
    fun cleared()
    fun callAndSaveLanguages()


    open class LanguagesRepositoryImpl(
        private val service: MoviesListService,
        private val dao: LanguageDao
    ) : LanguagesRepository {


        private val helper =
            PagingRequestHelper(
                Executors.newSingleThreadExecutor()
            )
        val networkState: MutableLiveData<NetworkState> = helper.createStatusLiveData()

        private val compositeDisposable: CompositeDisposable = CompositeDisposable()


        override fun getAllLanguages(): LiveData<List<LanguagesResponseBody>> {
            return dao.language()
        }

        override fun cleared() {
            compositeDisposable.clear()
        }

        override fun callAndSaveLanguages() {
            networkState.value =
                NetworkState.LOADING
            getLangaugesList()
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    dao.deleteAllLanguages()
                        .andThen(insertLanguages(it))
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


        fun insertLanguages(list: List<LanguagesResponseBody>): Completable {
            return dao.insertAllLanguages(list.map { it })
        }

        fun getLangaugesList(): Single<List<LanguagesResponseBody>> =
            service.getLanguages().map { it }


    }
}