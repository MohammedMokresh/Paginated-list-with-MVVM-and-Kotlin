package com.mok.moviespaginatedlist

import com.mok.moviespaginatedlist.models.MoviesListResponseBody
import com.mok.moviespaginatedlist.models.Result
import com.mok.moviespaginatedlist.utils.BaseSchedulers
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesListService {
    fun movies(page: Int,sizePage: Int): Single<List<Result>>

    class Network(
        private val retrofit: Retrofit,
        private val schedulers: BaseSchedulers
    ) : MoviesListService {

        override fun movies(page: Int,sizePage: Int): Single<List<Result>> {

            return retrofit.create(NetworkCalls::class.java)
                .getMoviesList(BuildConfig.API_KEY,"release_date.desc",page)
                .subscribeOn(schedulers.io())
                .map { it.results }
        }


        interface NetworkCalls {

            @GET("/discover/movie")
            fun getMoviesList(
                @Query("apikey") apiKey: String,
                @Query("sort_by") sortBy: String, @Query("page") page: Int
            ): Single<MoviesListResponseBody<List<Result>>>

        }

    }


}