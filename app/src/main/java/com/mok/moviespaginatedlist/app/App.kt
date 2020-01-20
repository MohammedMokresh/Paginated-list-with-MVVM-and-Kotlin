package com.mok.moviespaginatedlist.app

import android.app.Application
import com.mok.moviespaginatedlist.BuildConfig
import com.mok.moviespaginatedlist.di.AppKoinModules
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

open class App : Application() {


    override fun onCreate() {
        super.onCreate()

        initializeInjector()

        RxJavaPlugins.setErrorHandler {
            Timber.e("The error not be delivered to callback ${it.message} ")
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    open fun initializeInjector() {
        startKoin {
            // declare used Android context
            androidLogger()
            androidContext(this@App)
            modules(AppKoinModules.getModules())
        }

    }
}