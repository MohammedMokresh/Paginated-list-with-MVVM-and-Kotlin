package com.mok.moviespaginatedlist.di

import com.mok.moviespaginatedlist.BuildConfig
import com.mok.moviespaginatedlist.app.ApiServices
import com.mok.moviespaginatedlist.app.AppDatabase
import com.mok.moviespaginatedlist.genres.data.GenreRepository
import com.mok.moviespaginatedlist.genres.GenreViewModel
import com.mok.moviespaginatedlist.languages.data.LanguagesRepository
import com.mok.moviespaginatedlist.languages.LanguagesViewModel
import com.mok.moviespaginatedlist.moviesList.data.MovieRepository
import com.mok.moviespaginatedlist.moviesList.MoviesViewModel
import com.mok.moviespaginatedlist.utils.BaseSchedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val cacheModule by lazy {

    module {
        single {
            AppDatabase.getInstance(get()).genreDao()
        }
        single {
            AppDatabase.getInstance(get()).movieDAO()
        }
        single {
            AppDatabase.getInstance(get()).languageDao()

        }

    }
}


val repositoryModule by lazy {
    module {

        single<GenreRepository> {
            GenreRepository.GenreRepositoryImpl(get(), get())

        }
        single<MovieRepository> {
            MovieRepository.MoviesRepositoryImpl(get(), get())

        }
        single<LanguagesRepository> {
            LanguagesRepository.LanguagesRepositoryImpl(get(), get())

        }


    }
}


val appModule by lazy {
    module {
        single<BaseSchedulers> { BaseSchedulers.BaseSchedulersImpl() }
    }
}


val viewModelModule by lazy {
    module {
        viewModel {
            GenreViewModel(get())

        }
        viewModel {
            MoviesViewModel(get())

        }
        viewModel {
            LanguagesViewModel(get())

        }

    }

}


val serviceModule by lazy {

    module {

        single<ApiServices> { ApiServices.Network(get(), get()) }


        single<Retrofit> {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}