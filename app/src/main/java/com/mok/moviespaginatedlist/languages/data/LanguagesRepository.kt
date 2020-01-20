package com.mok.moviespaginatedlist.languages.data

import androidx.lifecycle.LiveData
import com.mok.moviespaginatedlist.app.ApiServices
import com.mok.moviespaginatedlist.languages.models.LanguagesResponseBody
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

interface LanguagesRepository {

    fun cleared()
    fun callAndSaveLanguages()
    fun getLanguageFromIso(iso: String): LiveData<LanguagesResponseBody>


    open class LanguagesRepositoryImpl(
        private val service: ApiServices,
        private val dao: LanguageDao
    ) : LanguagesRepository {

        private val compositeDisposable: CompositeDisposable = CompositeDisposable()

        override fun callAndSaveLanguages() {
            getLangaugesList()
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    dao.deleteAllLanguages()
                        .andThen(insertLanguages(it))
                }.subscribeBy().addTo(compositeDisposable)
        }

        override fun getLanguageFromIso(iso: String): LiveData<LanguagesResponseBody> {
            return dao.getLanguageFromISO(iso)

        }

        fun insertLanguages(list: List<LanguagesResponseBody>): Completable {
            return dao.insertAllLanguages(list.map { it })
        }

        fun getLangaugesList(): Single<List<LanguagesResponseBody>> =
            service.getLanguages().map { it }


        override fun cleared() {
            compositeDisposable.clear()
        }


    }
}