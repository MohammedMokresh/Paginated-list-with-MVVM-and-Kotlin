package com.mok.moviespaginatedlist.di

import com.mok.moviespaginatedlist.BuildConfig
import com.mok.moviespaginatedlist.MoviesListService
import com.mok.moviespaginatedlist.cache.AppDatabase
import com.mok.moviespaginatedlist.repository.MovieRepository
import com.mok.moviespaginatedlist.ui.MoviesViewModel
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
            AppDatabase.getInstance(get()).movieDAO()
        }
    }
}


val repositoryModule by lazy {
    module {
        single<MovieRepository> {
            MovieRepository.MoviesRepositoryImpl(get(), get())
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
            MoviesViewModel(get())
        }
    }
}


val serviceModule by lazy {

    module {

        single<MoviesListService> { MoviesListService.Network(get(), get()) }


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