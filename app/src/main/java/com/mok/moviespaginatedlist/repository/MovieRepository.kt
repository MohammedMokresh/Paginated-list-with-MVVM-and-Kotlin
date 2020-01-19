package com.mok.moviespaginatedlist.repository

import androidx.paging.toLiveData
import com.mok.moviespaginatedlist.MoviesListService
import com.mok.moviespaginatedlist.cache.MovieDao
import com.mok.moviespaginatedlist.models.Result
import com.mok.moviespaginatedlist.utils.liveData
import com.mok.moviespaginatedlist.utils.paginationUtils.GenericBoundaryCallback
import com.mok.moviespaginatedlist.utils.paginationUtils.Listing
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface MovieRepository {


    companion object {
        const val SIZE_PAGE = 20
    }


    fun getListable(): Listing<Result>

    fun deleteAllExceptfirstTen()

    open class MoviesRepositoryImpl(
        private val service: MoviesListService,
        private val dao: MovieDao
    ) : MovieRepository {


        override fun getListable(): Listing<Result> {
            return object :
                Listing<Result> {

                /** Create the boundary callback **/
                val bc: GenericBoundaryCallback<Result> by lazy {
                    GenericBoundaryCallback(
                        { dao.deleteAllMovies() },
                        { movie(it) },
                        { insertMovies(it) }
                    )
                }

                override fun getDataSource() =
                    dao.getAllPaged().map { it }.toLiveData(
                        pageSize = SIZE_PAGE,
                        boundaryCallback = bc
                    )


                override fun getBoundaryCallback() = liveData(bc)

            }
        }


        override fun deleteAllExceptfirstTen() {
            Completable.fromAction(this::releaseTheExtraCache)
                .subscribeOn(Schedulers.io())
                .subscribe()
        }


        private fun releaseTheExtraCache() {
            dao.deleteAllExceptfirstTen()
        }


        fun insertMovies(list: List<Result>): Completable {
            return dao.insertAllMovies(list.map { it })
        }

        fun movie(offset: Int): Single<List<Result>> =
            service.movies(offset)
                .map { it }

    }

}