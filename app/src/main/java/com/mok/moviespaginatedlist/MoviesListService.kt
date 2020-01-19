package com.mok.moviespaginatedlist

import com.mok.moviespaginatedlist.genres.Genre
import com.mok.moviespaginatedlist.genres.GenresResponseBody
import com.mok.moviespaginatedlist.languages.LanguagesResponseBody
import com.mok.moviespaginatedlist.models.MoviesListResponseBody
import com.mok.moviespaginatedlist.models.Result
import com.mok.moviespaginatedlist.utils.BaseSchedulers
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesListService {
    fun movies(page: Int): Single<List<Result>>

    fun getGenresList(): Single<List<Genre>>
    fun getLanguages(): Single<List<LanguagesResponseBody>>

    class Network(
        private val retrofit: Retrofit,
        private val schedulers: BaseSchedulers
    ) : MoviesListService {

        override fun movies(page: Int): Single<List<Result>> {

            return retrofit.create(NetworkCalls::class.java)
                .getMoviesList(BuildConfig.API_KEY, "release_date.desc", page)
                .subscribeOn(schedulers.io())
                .map { it.results }
        }

        override fun getGenresList(): Single<List<Genre>> {
            return retrofit.create(NetworkCalls::class.java)
                .getGenresList(BuildConfig.API_KEY).subscribeOn(schedulers.io()).map { it.genres }

        }

        override fun getLanguages(): Single<List<LanguagesResponseBody>> {
            return retrofit.create(NetworkCalls::class.java)
                .getLanguages(BuildConfig.API_KEY).subscribeOn(schedulers.io()).map { it }
        }


        interface NetworkCalls {

            @GET("discover/movie")
            fun getMoviesList(
                @Query("api_key") apiKey: String,
                @Query("sort_by") sortBy: String, @Query("page") page: Int
            ): Single<MoviesListResponseBody<List<Result>>>


            @GET("genre/movie/list")
            fun getGenresList(
                @Query("api_key") apiKey: String
            ): Single<GenresResponseBody<List<Genre>>>

            @GET("configuration/languages")
            fun getLanguages(
                @Query("api_key") apiKey: String
            ): Single<List<LanguagesResponseBody>>

        }

    }


}