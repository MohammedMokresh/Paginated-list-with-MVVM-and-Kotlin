package com.mok.moviespaginatedlist.genres.data

import androidx.lifecycle.LiveData
import com.mok.moviespaginatedlist.app.ApiServices
import com.mok.moviespaginatedlist.genres.models.Genre
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

interface GenreRepository {

    fun cleared()
    fun insertGenresInLocal()
    fun getGenreById(genreId: Int): LiveData<Genre>


    open class GenreRepositoryImpl(
        private val service: ApiServices,
        private val dao: GenreDao
    ) : GenreRepository {


        private val compositeDisposable: CompositeDisposable = CompositeDisposable()


        override fun insertGenresInLocal() {
            getGenresList()
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    dao.deleteAllGenres()
                        .andThen(insertGenres(it))
                }.subscribeBy().addTo(compositeDisposable)
        }

        override fun getGenreById(genreId: Int): LiveData<Genre> {
            return dao.getGenreById(genreId)

        }


        override fun cleared() {
            compositeDisposable.clear()
        }


        fun insertGenres(list: List<Genre>): Completable {
            return dao.insertAllGenres(list.map { it })
        }

        fun getGenresList(): Single<List<Genre>> =
            service.getGenresList().map { it }

    }


}